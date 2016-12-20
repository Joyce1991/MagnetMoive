package com.xxz.magnet.utils;

import android.text.TextUtils;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class HttpUtils {


    public static String decodeResponse(String value) {
        if(TextUtils.isEmpty(value))
            return "";

        byte[] data = Base64Utils.decode(value);
        byte[] resultData = SecurityUtils.decrypt(data);
        return new String(resultData);
    }

    /**
     * 接口key生成规则
     *
     * @param params 请求参数
     * @return
     */
    public static String getKyxKey(String params) {
        String first = SecurityUtils.getMd5("api.kuaiyouxi.com@youxikyxlaile");
        String second = params;
        String key = SecurityUtils.getMd5(first + second);
        return key;
    }

    public static String getKyxPostKey() {
        String first = SecurityUtils.getMd5("api.kuaiyouxi.com@youxikyxlaile");
        return first;
    }

    public static String convertObjectParams(HashMap<String, Object> params) {
        try {

            StringBuilder sb = new StringBuilder();

            if (params != null && params.size() > 0) {
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    Object value = entry.getValue();
                    value = value == null ? "" : value;
                    sb.append(entry.getKey()).append("=")
                            .append(URLEncoder.encode(String.valueOf(value), "UTF-8")).append("&");
                }
                sb.deleteCharAt(sb.length() - 1);
            }
            return sb.toString();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }


}
