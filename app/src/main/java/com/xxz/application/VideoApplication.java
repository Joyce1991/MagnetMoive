package com.xxz.application;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.xxz.bussiness.concurrent.SmartExecutor;
import com.zhy.http.okhttp.OkHttpUtils;
import okhttp3.OkHttpClient;

import java.util.concurrent.TimeUnit;

public class VideoApplication extends Application {
    private static VideoApplication mContext;
    private static Handler mMainHandler = new Handler();

    private SmartExecutor mExecutor = new SmartExecutor();

    public SmartExecutor getExecutor() {
        if (mExecutor == null)
            mExecutor = new SmartExecutor();

        return mExecutor;
    }

    public static Context getContext() {
        return mContext;
    }

    public static VideoApplication getInstance() {
        return mContext;
    }

    public static void runOnMainThread(Runnable runner) {
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            runner.run();
        } else {
            if (mMainHandler == null)
                mMainHandler = new Handler();
            mMainHandler.post(runner);
        }
    }

    public static void postDelayed(Runnable runnable, long delayMillis) {
        if (mMainHandler == null)
            mMainHandler = new Handler();
        mMainHandler.postDelayed(runnable, delayMillis);
    }

    public static void removeHandlerCallbacks(Runnable runnable) {
        if (mMainHandler == null)
            mMainHandler = new Handler();
        mMainHandler.removeCallbacks(runnable);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        //LeakCanary.install(this);
    }

    private void init() {
        initOkhttp();
    }

    private void initOkhttp() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);
    }

}
