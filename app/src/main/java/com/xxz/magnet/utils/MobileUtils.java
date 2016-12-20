package com.xxz.magnet.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * 工具类
 * 1、获取设备相关信息  get
 * 2、获取当前设备环境  is
 *
 * @author chengweixin
 *         modify by YangGuoming on 15-12-02
 */
public class MobileUtils {

    public static String gpuModel = "";//，应用闪屏页初始化
    private static String cpu = "";//CPU型号

    /**
     * 获取设备内存大小 数组0：内存总大小 数组1：内存剩余大小
     *
     * @param context
     * @return
     */
    public static Long[] getTotalMemory(Context context) {
        Long[] result = {0l, 0l}; // 1-total 2-avail
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        MemoryInfo mi = new MemoryInfo();
        am.getMemoryInfo(mi);
        long mTotalMem = 0;
        long mAvailMem = mi.availMem;
        String str1 = "/proc/meminfo";
        String str2;
        String[] arrayOfString;
        try {
            FileReader localFileReader = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(localFileReader, 8192);
            str2 = localBufferedReader.readLine();
            arrayOfString = str2.split("\\s+");
            mTotalMem = Long.parseLong(arrayOfString[1]) * 1024;
            localBufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        result[0] = mTotalMem;
        result[1] = mAvailMem;
        return result;
    }


    /**
     * 获取设备，wifi mac地址
     *
     * @param context
     * @return
     */
    public static String getLocalMacAddress(Context context) {
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        return info.getMacAddress();
    }

    /**
     * 获取设备系统版本号
     *
     * @return
     */
    public static String getBuildRelease() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取设备系统当前使用的语言 设置成简体中文的时候，getLanguage()返回的是zh,getCountry()返回的是cn.
     *
     * @return
     */
    public static String getLanguage() {
        return Locale.getDefault().getLanguage();

    }

    /**
     * 获取设备系统当前使用的语言 设置成简体中文的时候，getLanguage()返回的是zh,getCountry()返回的是cn.
     *
     * @return
     */
    public static String getCountry() {
        return Locale.getDefault().getCountry();
    }

    /**
     * 获取设备，SIM卡的国家码，用于判断SIM卡是否是国内的
     *
     * @param mContext
     * @return
     */
    public static String getSimCountry(Context mContext) {
        TelephonyManager telManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        String simCode = telManager.getSimCountryIso();
        return simCode;
    }

    /**
     * 获取设备系统sdk级别
     *
     * @return
     */
    public static int getBuildSdk() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 获取设备固件版本
     *
     * @return
     */
    public static String getBuildFingerprint() {
        return Build.FINGERPRINT;
    }

    /**
     * 获取设备是否有root环境
     *
     * @return
     */
    public static boolean haveRoot() {
        try {
            File file = new File("/system/bin/su");
            File file2 = new File("/system/xbin/su");
            return file.exists() || file2.exists();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return false;

    }

    /**
     * 获取型号
     *
     * @return
     */
    public static String getModel() {
        return Build.MODEL;
    }

    /**
     * 获取设备系统版本号
     *
     * @return
     */
    public static String getSystemVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取设备品牌
     *
     * @return
     */
    public static String getBrand() {
        return Build.MANUFACTURER;
    }

    /**
     * 判断当前设备网络是否是WIFI网络
     *
     * @param context
     * @return
     */
    public static boolean isWifi(Context context) {
        ConnectivityManager connectMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectMgr.getActiveNetworkInfo();

        if (info == null) { // 没有网络连接
            return false;
        }
        if (info.getType() == ConnectivityManager.TYPE_WIFI) { // WIFI 网络
            return true;
        }
        return false; // 不能识别网络
    }

    /**
     * 判断当前设备网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isNetworkEnable(Context context) {
        return networkEnable(context);
    }

    private static boolean networkEnable(Context context) {
        try {
            ConnectivityManager conn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo workInfo = conn.getActiveNetworkInfo();
            if (workInfo != null) {
                return workInfo.isConnected();
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 判断是否是手机, 触碰
     *
     * @return
     */
    public static boolean isMobile(Context context) {
        boolean isModel = false;
        String model = Build.MODEL;
        if (model.startsWith("DM")) {
            isModel = true;
        }
        if (model.startsWith("DB")) {
            isModel = true;
        }
        if (MobileUtils.isTouchScreen(context) && !isModel) {
            return true;
        }

        return false;
    }

    public static boolean isTouchScreen(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            FeatureInfo[] infos = pm.getSystemAvailableFeatures();

            for (FeatureInfo featureInfo : infos) {
                if (featureInfo.name != null && featureInfo.name.equals("android.hardware.touchscreen")) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

    /**
     * 获取IMEI
     *
     * @param context
     * @return
     */
    public static String getIMEI(Context context) {

        try {
            TelephonyManager tm = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            String imei = tm.getDeviceId();
            if (!TextUtils.isEmpty(imei)) {
                return imei;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 判断是否是sdk的游戏版本
     *
     * @param cxt
     * @param packageName
     * @return
     */
    public static boolean isVersionSdk(Context cxt, String packageName) {
        ApplicationInfo info;
        try {
            info = cxt.getPackageManager().getApplicationInfo(
                    packageName, PackageManager.GET_META_DATA);
            String msg = info.metaData.getString("kyx_channel");
            if (!TextUtils.isEmpty(msg) && msg.startsWith("kyx_videosdk")) {
                return true;
            }
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static final String PATH_CPU = "/sys/devices/system/cpu/";
    private static final String CPU_FILTER = "cpu[0-9]+";
    private static int CPU_CORES = 0;
    /**
     * Gets the number of cores available in this device, across all processors.
     * Requires: Ability to peruse the filesystem at "/sys/devices/system/cpu"
     *
     * @return The number of cores, or available processors if failed to get result
     */
    public static int getCoresNumbers() {
        if (CPU_CORES > 0) {
            return CPU_CORES;
        }
        //Private Class to display only CPU devices in the directory listing
        class CpuFilter implements FileFilter {
            @Override
            public boolean accept(File pathname) {
                //Check if filename is "cpu", followed by a single digit number
                if (Pattern.matches(CPU_FILTER, pathname.getName())) {
                    return true;
                }
                return false;
            }
        }
        try {
            //Get directory containing CPU info
            File dir = new File(PATH_CPU);
            //Filter to only list the devices we care about
            File[] files = dir.listFiles(new CpuFilter());
            //Return the number of cores (virtual CPU devices)
            CPU_CORES = files.length;
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (CPU_CORES < 1) {
            CPU_CORES = Runtime.getRuntime().availableProcessors();
        }
        if (CPU_CORES < 1) {
            CPU_CORES = 1;
        }
        LogUtils.v(String.format("CPU cores: %d", CPU_CORES));
        return CPU_CORES;
    }

}
