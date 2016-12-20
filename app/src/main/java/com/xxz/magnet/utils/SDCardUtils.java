package com.xxz.magnet.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import com.xxz.application.VideoApplication;

/**
 * Created with Android Studio
 * </p>
 * Authour:xiaxf
 * </p>
 * Date:15/12/9.
 */

public class SDCardUtils {
    private static final String EXTERNAL_STORAGE_PERMISSION = "android.permission.WRITE_EXTERNAL_STORAGE";

    public static boolean isSDCardAvailable(){
        String externalStorageState;
        try {
            externalStorageState = Environment.getExternalStorageState();
        } catch (Exception e) { // (sh)it happens (Issue #660)
            externalStorageState = "";
        }
        return Environment.MEDIA_MOUNTED.equals(externalStorageState) && hasExternalStoragePermission(VideoApplication.getContext());
    }

    private static boolean hasExternalStoragePermission(Context context) {
        int perm = context.checkCallingOrSelfPermission(EXTERNAL_STORAGE_PERMISSION);
        return perm == PackageManager.PERMISSION_GRANTED;
    }
}
