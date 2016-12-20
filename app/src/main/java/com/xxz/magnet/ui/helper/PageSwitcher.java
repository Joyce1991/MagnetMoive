package com.xxz.magnet.ui.helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import com.xxz.magnet.ui.activity.SubActivity;

/**
 * Created with Android Studio.
 * <p/>
 * Author:xiaxf
 * <p/>
 * Date:2015/8/13.
 */
public class PageSwitcher {
    public final static String INTENT_EXTRA_FRAGMENT_TYPE = "fragment_type";
    public final static String INTENT_EXTRA_FRAGMENT_ARGS = "args";
    public final static String BUNDLE_FRAGMENT_ANIM = "anim";

    public static void switchToPage(Context context, int fragmentType, Bundle bundle, int intentFlag) {
        Intent intent = new Intent(context, SubActivity.class);
        if (intentFlag != 0) {
            intent.setFlags(intentFlag);
        }
        intent.putExtra(INTENT_EXTRA_FRAGMENT_TYPE, fragmentType);
        if (bundle != null) {
            intent.putExtra(INTENT_EXTRA_FRAGMENT_ARGS, bundle);
        }

        context.startActivity(intent);
    }

    public static void switchToPage(Context context, int fragmentType) {
        switchToPage(context, fragmentType, null, 0);
    }

    public static void switchToPage(Context context, int fragmentType, Bundle bundle) {
        switchToPage(context, fragmentType, bundle, 0);
    }

    public static void switchToPageForResult(Activity activity, int fragmentType, int requestCode) {
        switchToPageForResult(activity, fragmentType, requestCode, null, 0);
    }

    public static void switchToPageForResult(Activity activity, int fragmentType, int requestCode, Bundle bundle) {
        switchToPageForResult(activity, fragmentType, requestCode, bundle, 0);
    }

    public static void switchToPageForResult(Activity context, int fragmentType, int requestCode, Bundle bundle, int intentFlag) {
        Intent intent = new Intent(context, SubActivity.class);
        if (intentFlag != 0) {
            intent.setFlags(intentFlag);
        }
        intent.putExtra(INTENT_EXTRA_FRAGMENT_TYPE, fragmentType);
        if (bundle != null) {
            intent.putExtra(INTENT_EXTRA_FRAGMENT_ARGS, bundle);
        }

        context.startActivityForResult(intent, requestCode);
    }

    /**
     * 跳转到系统设置页
     *
     * @param context
     */
    public static void switchToSettingPage(Context context) {
        Intent settingsIntent = new Intent(Settings.ACTION_SETTINGS);
        context.startActivity(settingsIntent);
    }

}
