package com.xxz.bussiness.network;


import com.xxz.application.VideoApplication;
import com.xxz.magnet.R;

/**
 * Created with Android Studio
 * </p>
 * Authour:xiaxf
 * </p>
 * Date:16/9/9.
 */

public enum HttpError {
    /**
     * 未知错误
     */
    UNKNOWN_EXCEPTION(0),

    /**
     * 空返回
     */
    EMPTY_EXCEPTION(1),

    /**
     * 解析错误
     */
    PARSE_EXCEPTION(2),

    /**
     * IO异常
     */
    IO_EXCEPTION(3),

    /**
     * 无网络
     */
    NETWORK_DISABLE_EXCEPTION(4),


    /**
     *  无法连接服务器
     */
    UNKNOWNHOST_EXCEPTION(5);

    private int code;
    HttpError(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static String getHttpErrorMsg(HttpError error) {
        switch (error) {
            case EMPTY_EXCEPTION:
                return VideoApplication.getInstance().getString(R.string.err_no_new_data);

            case PARSE_EXCEPTION:

            case IO_EXCEPTION:
                return VideoApplication.getInstance().getString(R.string.err_io);

            case NETWORK_DISABLE_EXCEPTION:
                return VideoApplication.getInstance().getString(R.string.err_no_network);

            case UNKNOWNHOST_EXCEPTION:
                return VideoApplication.getInstance().getString(R.string.err_server_failed);

        }
        return VideoApplication.getInstance().getResources().getString(R.string.err_unknown);
    }

}
