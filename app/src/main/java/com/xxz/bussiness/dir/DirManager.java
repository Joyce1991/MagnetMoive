package com.xxz.bussiness.dir;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import com.xxz.application.VideoApplication;
import com.xxz.magnet.utils.LogUtils;
import com.xxz.magnet.utils.SDCardUtils;

import java.io.File;
import java.io.IOException;

/**
 * 目录管理类
 * Created with Android Studio.
 * <p>
 * Author:xiaxf
 * <p>
 * Date:2015/7/20.
 */
public class DirManager {
    public static final int DIR_EXTERNAL = 0;//外置存储根目录
    public static final int DIR_INTERNAL = 1; //内置存储根目录
    public static final int DIR_CUSTOM = 2; //自定义目录

    public static final String DIR_IMAGES = "images";
    public static final String DIR_VIDEO = "videos";
    public static final String DIR_DOWNLOAD = "downloads";
    public static final String DIR_CACHE = "caches";

    public static final String CUSOM_ROOT_DIR = "magnet";//自定义根目录


    /**
     * 获取默认文件存储路径
     *
     * @param childDir 子目录名
     * @param dirType  根目录类型
     * @return 路径:Android/data/包名/files/childDir
     */
    public static File getFilesDir(int dirType, String childDir) {
        Context context = VideoApplication.getContext();
        File baseDir = null;

        switch (dirType) {
            case DIR_EXTERNAL:
                if (SDCardUtils.isSDCardAvailable()) {
                    baseDir = new File(context.getExternalCacheDir(), childDir);
                    if (baseDir == null) {
                        File dataDir = new File(new File(Environment.getExternalStorageDirectory(), "Android"), "data");
                        File appFileDir = new File(new File(dataDir, context.getPackageName()), "files");
                        if (!appFileDir.exists()) {
                            appFileDir.mkdirs();
                            try {
                                new File(appFileDir, ".nomedia").createNewFile();
                            } catch (IOException e) {
                                LogUtils.i("Can't create \".nomedia\" file in application external cache directory");
                            }
                        }
                    }
                }

                break;

            case DIR_INTERNAL:
                baseDir = new File(context.getCacheDir(), childDir);
                break;
        }

        if (baseDir == null) {
            File rootFile = context.getFilesDir();
            if (rootFile == null) {
                String cacheDirPath = "/data/data/" + context.getPackageName() + "/files";
                rootFile = new File(cacheDirPath);
            }
            baseDir = new File(rootFile, childDir);
        }
        if (!baseDir.exists()) {
            baseDir.mkdirs();
        }
        return baseDir;
    }

    /**
     * 默认缓存存储路径
     *
     * @param dirType  内置还是外置
     * @param childDir 子目录名
     * @return 路径:Android/data/包名/files/caches/childDir
     */
    public static File getCachesDir(int dirType, String childDir) {
        return getFilesDir(dirType, DIR_CACHE + "/" + childDir);
    }

    /**
     * 外置缓存目录
     *
     * @param childDir 子目录明
     * @return 路径:Android/data/包名/files/caches/childDir
     */
    public static File getExternalCachesDir(String childDir) {
        return getCachesDir(DIR_EXTERNAL, childDir);
    }

    /**
     * 获取外置缓存目录
     *
     * @param childDir 子目录名
     * @return 路径:Android/data/包名/files/childDir
     */
    public static File getExternalFilesDir(String childDir) {
        return getFilesDir(DIR_EXTERNAL, childDir);
    }

    /**
     * 获取默认下载存储路径
     *
     * @param childDir 子目录名
     * @return 路径:Android/data/包名/files/downloads/childDir 如果childDir为空则返回Android/data/包名/files/downloads
     */
    public static File getDownloadDir(String childDir) {
        File file;
        File baseDir = getExternalFilesDir(DIR_DOWNLOAD);
        if (TextUtils.isEmpty(childDir)) {
            return baseDir;
        }
        file = new File(baseDir, childDir);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    /**
     * 图片缓存目录
     *
     * @return file
     */
    public static File getImageFilesDir() {
        return getExternalFilesDir(DIR_IMAGES);
    }

    /**
     * 消息缓存
     *
     * @return file
     */
    public static File getMessageFileDir() {
        return getExternalFilesDir("messages");
    }

    /**
     * 播放历史记录
     *
     * @return file
     */
    public static File getVideoHistoryDir() {
        return getExternalFilesDir(DIR_VIDEO + "/history");
    }

    /**
     * 播放收藏记录
     *
     * @return file
     */
    public static File getVideoCollectDir() {
        return getExternalFilesDir(DIR_VIDEO + "/collect");
    }

    /**
     * APK下载目录
     *
     * @return file
     */
    public static File getApkDownloadDir() {
        return getDownloadDir("apk");
    }

    /**
     * 网络请求的数据缓存目录
     *
     * @return file
     */
    public static File getServerCacheDir() {
        return getFilesDir(DIR_INTERNAL, "servers");
    }

    /**
     * 本地的数据缓存目录
     *
     * @return file
     */
    public static File getNativeCacheDir() {
        return getFilesDir(DIR_EXTERNAL, "native");
    }

    /**
     * 本地应用的缓存
     *
     * @return file
     */
    public static File getNativePackagesFileDir() {
        return getExternalFilesDir("packages");
    }

    /**
     * 搜索历史的缓存
     *
     * @return file
     */
    public static File getSearchHistoryFileDir() {
        return getExternalFilesDir("search_history");
    }


    /**
     * 游戏点击之后排序
     *
     * @return file
     */
    public static File getGameSortDir() {
        return getExternalFilesDir("game_sort");
    }

    /**
     * 所有请求缓存目录
     * @return
     */
    public static File getRequestCacheDir() {
        return getCachesDir(DIR_INTERNAL, "request");
    }

}
