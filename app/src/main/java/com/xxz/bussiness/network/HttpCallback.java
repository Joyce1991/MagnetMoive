package com.xxz.bussiness.network;

import android.support.v4.app.Fragment;
import android.text.TextUtils;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.xxz.application.VideoApplication;
import com.xxz.beans.PageItem;
import com.xxz.beans.ResponseResult;
import com.xxz.bussiness.cache.CacheManager;
import com.xxz.magnet.utils.HttpUtils;
import com.xxz.magnet.utils.LogUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import okhttp3.Call;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;

/**
 * Created with Android Studio
 * </p>
 * Authour:xiaxf
 * </p>
 * Date:16/3/2.
 */

public abstract class HttpCallback<T> extends StringCallback {
    private String cacheUrl;
    private String alias;
    private boolean needEncrypt;
    private boolean putCacheAfterResponse;
    private boolean noPutcacheIfEmpty; //如果内容为空则不存缓存
    private WeakReference<Fragment> mWeekFragment;

    public HttpCallback() {
    }

    /**
     * 设置Fragment防止 not attached to Activity的w问题
     * @param fragment
     */
    public HttpCallback(Fragment fragment) {
        if (fragment != null) {
            mWeekFragment = new WeakReference<>(fragment);
        }
    }

    @Override
    public void onResponse(String response, int id) {
        if (doNext()) {
            T result = parseResponse(response);
            postResult(result);
        }
    }

    @Override
    public void onError(Call call, Exception e, int id) {
        if (doNext()) {
            postError(e);
        }
    }

    public HttpCallback<T> noPutcacheIfEmpty(boolean noPutcacheIfEmpty) {
        this.noPutcacheIfEmpty = noPutcacheIfEmpty;
        return this;
    }

    public void setCacheUrl(String cacheUrl) {
        this.cacheUrl = cacheUrl;
    }

    public HttpCallback<T> setNeedEncrypt(boolean needEncrypt) {
        this.needEncrypt = needEncrypt;
        return this;
    }

    public HttpCallback<T> setAlias(String alias) {
        this.alias = alias;
        return this;
    }

    /**
     * 响应成功之后是否写缓存
     * @param putCache
     * @return
     */
    public HttpCallback<T> putCache(boolean putCache) {
        this.putCacheAfterResponse = putCache;
        return this;
    }

    private void writeCache(T t) {
        if (!putCacheAfterResponse || TextUtils.isEmpty(cacheUrl)) {
            return;
        }

        if (noPutcacheIfEmpty) {//如果数据为空则不存缓存
            if (t instanceof PageItem) {
                PageItem pageItem = (PageItem) t;
                if (pageItem.getRows() == null || pageItem.getRows().size() == 0)
                    return;
            } else if (t instanceof ResponseResult) {
                ResponseResult result = (ResponseResult) t;
                if (result.getRows() == null || result.getRows().size() == 0)
                    return;
            }
        }
        final String resopne = new Gson().toJson(t);
        final CacheManager instance = CacheManager.getInstance();
        VideoApplication.getInstance().getExecutor().execute(new Runnable() {
            @Override
            public void run() {
                if (instance.saveCache(resopne, cacheUrl)) {
                    LogUtils.v(String.format("put cache alias:%s", alias));
                }
            }
        });
    }


    public abstract void onSuccess(T result, boolean cache);

    public abstract void onFailed(HttpError httpError);

    public void onEmpty(){}

    private T parseResponse(String response) {
        Type type = getType();
        T result = null;
        if (response == null) {
            postError(new SocketException("null response"));
            return null;
        }
        try {
            if (this.needEncrypt) {
                response = HttpUtils.decodeResponse(response);
            }
            Gson gson = new Gson();
            result = gson.fromJson(response, type);

        } catch (Exception e) {
            postError(e);
        }
        return result;
    }

    private void postError(Exception e) {
        LogUtils.e(e);
        HttpError error = HttpError.UNKNOWN_EXCEPTION;
        if (e instanceof IOException) {
            error = HttpError.IO_EXCEPTION;
            if (e instanceof UnknownHostException) {
                error = HttpError.UNKNOWNHOST_EXCEPTION;
            }
        } else if (e instanceof JsonParseException) {
            error = HttpError.PARSE_EXCEPTION;
        } else if (!NetworkStateManager.isNetworkAvailable()) {
            error = HttpError.NETWORK_DISABLE_EXCEPTION;
        }

        onFailed(error);
    }

    private void postResult(T result) {
        // 返回的数据为空过滤
        if (result != null) {
            if (result instanceof ResponseResult) {
                ResponseResult<?> response = (ResponseResult<?>) result;
                List<?> rows = response.getRows();
                if (rows == null || rows.size() == 0) {
                    LogUtils.v("empty response alias:%s", alias);
                    onEmpty();
                    return;
                }
            }
            onSuccess(result, false);
            writeCache(result);
        } else {
            onFailed(HttpError.NETWORK_DISABLE_EXCEPTION);
        }
    }

    public Type getType() {
        Type type;
        Class<?> clazz = this.getClass();
        try {
            ParameterizedType mType = (ParameterizedType) clazz.getGenericInterfaces()[0];
            type = mType.getActualTypeArguments()[0];
        } catch (Throwable e) {
            type = ((ParameterizedType) clazz.getGenericSuperclass()).getActualTypeArguments()[0];
        }
        return type;
    }

    private boolean doNext() {
        if (mWeekFragment != null) {
            Fragment fragment = mWeekFragment.get();
            if (fragment == null) {
                return false;
            } else {
                if (fragment.getActivity() == null || !fragment.isAdded()) {
                    return false;
                }
            }

        }
        return true;
    }
}
