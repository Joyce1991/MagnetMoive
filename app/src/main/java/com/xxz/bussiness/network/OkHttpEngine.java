package com.xxz.bussiness.network;

import android.text.TextUtils;
import com.xxz.config.ApiUrl;
import com.zhy.http.okhttp.OkHttpUtils;
import okhttp3.OkHttpClient;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;


/**
 * Create With AndroidStudio
 * Author chenql
 * Data  2016-8-18.
 */
public class OkHttpEngine {

    static {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }

    private Builder mBuilder;
    public static final int MODE_GET = 0;//get请求
    public static final int MODE_POST = 1;//post请求
    public static final int TYPE_OWN = 2;//我们自己逻辑参数

    private OkHttpEngine(Builder builder) {
        mBuilder = builder;
    }

    public <T> void execute(HttpCallback<T> callback) {
        switch (mBuilder.mode) {
            case MODE_GET:
                //doGet(callback);
                break;

            case MODE_POST:
                //doPost(callback);
                break;
        }
    }

    //private  <T> void doGet(HttpCallback<T> callback) {
    //    String requestUrl = "";
    //    requestUrl = HttpUtils.createGetUrl(KyxSDKGlobal.mContext, mBuilder.url, mBuilder.params);
    //    callback.setNeedEncrypt(true);
    //    callback.setAlias(mBuilder.alias);
    //    callback.setCacheUrl(requestUrl);
    //    if (mBuilder.loadCache) {
    //        T result = CacheManager.getInstance().loadCache(requestUrl, callback.getType());
    //        if (result != null) {
    //            callback.onSuccess(result, mBuilder.loadCache);
    //            return;
    //        }
    //
    //    }
    //
    //    LogUtils.v("get request alias:%s #url:%s", mBuilder.alias, requestUrl);
    //
    //    OkHttpUtils.get()
    //            .url(requestUrl)
    //            .tag(mBuilder.tag)
    //            .build()
    //            .execute(callback);
    //}


    //private <T> void doPost(HttpCallback<T> callback) {
    //    HashMap<String, String> params = HttpUtils.createPostParams(KyxSDKGlobal.mContext, mBuilder.params);
    //    OkHttpUtils.post()
    //            .url(mBuilder.url)
    //            .tag(mBuilder.tag)
    //            .params(params)
    //            .build()
    //            .execute(callback);
    //}


    public static class Builder {
        public int mode = MODE_GET;//默认get请求
        public String url;
        public String tag;
        public boolean needEncrypt = ApiUrl.NEED_ENCRYPT;//是否加密,默认加密
        public String alias = "";//过滤标志
        public int type = TYPE_OWN;//默认我们自己的广告类型
        public boolean loadCache = false;//是否缓存
        public int cacheTime;//缓存事件
        public HashMap<String, String> params = new HashMap<>();

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder mode(int mode) {
            this.mode = mode;
            return this;
        }

        public Builder tag(String tag) {
            this.tag = tag;
            return this;
        }

        public Builder addParams(String key, String value) {
            params.put(key, value);
            return this;
        }

        public Builder addParams(String key, int value) {
            params.put(key, String.valueOf(value));
            return this;
        }

        public Builder addParams(String key, long value) {
            params.put(key, String.valueOf(value));
            return this;
        }

        public Builder setParams(HashMap<String, String> params){
            this.params = params;
            return this;
        }

        public Builder needEncrypt(boolean needEncrypt) {
            this.needEncrypt = needEncrypt;
            return this;
        }

        public Builder loadCache(boolean loadCache) {
            this.loadCache = loadCache;
            return this;
        }

        public Builder alias(String alias) {
            this.alias = alias;
            return this;
        }

        public Builder cacheTime(int cacheTime) {
            this.cacheTime = cacheTime * 1000;
            return this;
        }

        public OkHttpEngine build() {
            return new OkHttpEngine(this);
        }
    }

    /**
     * 根据 tag取消请求
     *
     * @param tag
     */
    public static void cancelRequestByTag(String tag) {
        if (TextUtils.isEmpty(tag))
            return;

        OkHttpUtils.getInstance().cancelTag(tag);
    }


    /**
     * 根据url取消单个请求
     *
     * @param url
     */
    public static void cancelRequestByUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        OkHttpUtils.get().url(url).build().cancel();
    }
}
