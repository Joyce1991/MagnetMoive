package com.xxz.magnet.utils;

import android.os.Handler;
import android.text.TextUtils;
import android.widget.Toast;
import com.xxz.application.VideoApplication;

/**
 * Created by starrysky on 14-12-16.
 */
public class ToastUtils {

    private static Toast mToast;
    private static Handler mHandler = new Handler();
    private static Runnable r = new Runnable() {
        public void run() {
            mToast.cancel();
            mToast = null;//toast隐藏后，将其置为null
        }
    };

    public static void toast(final String msg) {
        if (TextUtils.isEmpty(msg)) return;

        VideoApplication.runOnMainThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(VideoApplication.getContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void toast(final int sid) {
        VideoApplication.runOnMainThread(new Runnable() {
            @Override
            public void run() {
                toast(VideoApplication.getContext().getResources().getString(sid));
            }
        });
    }

    /**
     * 防止重复显示toast，传字符串 todo 逻辑改好后再一次修改所有接口!!!!
     *
     * @param message
     */
    public static void showToast(String message) {
        if (TextUtils.isEmpty(message)) return;

        mHandler.removeCallbacks(r);
        if (mToast == null) {
            mToast = Toast.makeText(VideoApplication.getContext(), message, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(message);
        }
        mHandler.postDelayed(r, 2000);//延迟1秒隐藏toast
        mToast.show();
    }

    /**
     * 防止重复显示toast，传资源id
     *
     * @param sid
     */
    public static void showToast(int sid) {
        mHandler.removeCallbacks(r);
        if (mToast == null) {
            mToast = Toast.makeText(VideoApplication.getContext(), VideoApplication.getContext().getResources().getString(sid), Toast.LENGTH_SHORT);
        } else {
            mToast.setText(VideoApplication.getContext().getResources().getString(sid));
        }
        mHandler.postDelayed(r, 2000);//延迟1秒隐藏toast
        mToast.show();
    }
}
