package com.xxz.bussiness.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import com.xxz.application.VideoApplication;

/**
 * Created with IntelliJ IDEA.
 * User: xiejm
 * Date: 8/21/12
 * Time: 2:46 PM
 */
public class NetworkStateManager {

    private static NetworkState mNetworkState;

    private static NetworkStateManager mInstance;

    public static synchronized void startListen() {
        if (mInstance == null) {
            mInstance = new NetworkStateManager();
        }
    }

    private NetworkStateManager() {
    }


    public static NetworkState getNetworkState() {
        mNetworkState = getNetworkState(VideoApplication.getContext());
        return mNetworkState;
    }

    public static boolean isNetworkAvailable() {
        mNetworkState = getNetworkState(VideoApplication.getContext());
        return mNetworkState != NetworkState.UNAVAILABLE;
    }

    private static NetworkState getNetworkState(Context context) {
        NetworkState state = null;
        NetworkInfo info = null;

        try {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            info = manager.getActiveNetworkInfo();

            if (info == null) {//android2.2 sometimes getActiveNetworkInfo always return null, but network is available
                NetworkInfo wifiInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                NetworkInfo mobileInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

                if (wifiInfo != null && wifiInfo.isAvailable()) {
                    info = wifiInfo;
                } else if (mobileInfo != null && mobileInfo.isAvailable()) {
                    info = mobileInfo;
                }
            }

            if (info != null && (info.isConnectedOrConnecting() || info.isRoaming())) {
                if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                    state = NetworkState.WIFI;
                }

                if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                    if (info.getSubtype() <= 4) {
                        state = isWapConnection(info.getExtraInfo()) ? NetworkState.NET_2G_WAP : NetworkState.NET_2G; // ~ 50-100 kbps

                    } else {
                        state = NetworkState.NET_3G;
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (state == null) {
            state = NetworkState.UNAVAILABLE;
        }

        if (state == NetworkState.WIFI) {
            state.setExtra("wifi");
        } else if (info != null) {
            state.setExtra(getExtra(info));
        }

        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String operator = tm.getNetworkOperatorName();

        if (operator == null || operator.length() == 0) {
            state.setOperator("unknown");
        } else {
            state.setOperator(operator);
        }

        return state;
    }

    private static String getExtra(NetworkInfo info) {
        String extra = info.getExtraInfo();

        if (extra == null) {
            extra = info.getSubtypeName();
        }

        return extra;
    }

    private static boolean isWapConnection(String extraInfo) {
        return extraInfo != null && extraInfo.contains("wap");
    }
}