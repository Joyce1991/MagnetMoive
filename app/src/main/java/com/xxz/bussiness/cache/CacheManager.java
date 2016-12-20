package com.xxz.bussiness.cache;

import com.google.gson.Gson;
import com.xxz.bussiness.dir.DirManager;
import com.xxz.magnet.utils.FileUtils;
import com.xxz.magnet.utils.HttpUtils;
import com.xxz.magnet.utils.SecurityUtils;

import java.io.File;
import java.lang.reflect.Type;

/**
 * Created with Android Studio
 * </p>
 * Authour:xiaxf
 * </p>
 * Date:16/3/4.
 */

public class CacheManager {
    private boolean needEencypt = false;
    private static CacheManager ourInstance = new CacheManager();

    public static CacheManager getInstance() {
        return ourInstance;
    }

    private CacheManager() {
    }

    public <T> T loadCache(String url, Type type) {
        String cacheContent;
        synchronized (CacheManager.this) {
            cacheContent = FileUtils.readFileAsString(getCacheFile(url));
        }
        if (needEencypt) {
            cacheContent = HttpUtils.decodeResponse(cacheContent);
        }
        try {
            return new Gson().fromJson(cacheContent, type);
        } catch (Exception e) {
        }
        return null;
    }

    private File getCacheFile(String url) {
        String cachePath = DirManager.getRequestCacheDir().getAbsolutePath() + "/" + SecurityUtils.getMd5(url, "UTF-8");
        return new File(cachePath);
    }

    public <T> boolean saveCache(String response, String url) {
        String reuslt = response;
        if (needEencypt) {
            reuslt = SecurityUtils.getMd5(response, "UTF-8");
        }
        synchronized (CacheManager.this) {
            return FileUtils.writeFile(getCacheFile(url).getAbsolutePath(), reuslt);
        }
    }

    public long getCacheTime(String url) {
        return getCacheFile(url).lastModified();
    }
}
