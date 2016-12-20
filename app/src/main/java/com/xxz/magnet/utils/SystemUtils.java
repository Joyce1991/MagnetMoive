package com.xxz.magnet.utils;

import java.io.Closeable;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.DecimalFormat;

/**
 * Created with Android Studio
 * </p>
 * Authour:xiaxf
 * </p>
 * Date:15/11/25.
 */

public class SystemUtils {

    public static String toTenThousand(int num) {

        if (num < 10000) {
            return num + "";
        }

        DecimalFormat decimalFormat = new DecimalFormat("0.0");//构造方法的字符格式这里如果小数不足1位,会以0补足.

        float i = (float) num / 10000;
        return decimalFormat.format(i) + "万";

    }

    /**
     * 关闭IO流
     *
     * @param obj
     */
    public static void closeCloseable(Closeable obj) {
        try {
            // 修复小米MI2的JarFile没有实现Closeable导致崩溃问题
            if (obj != null && obj instanceof Closeable)
                obj.close();

        } catch (IOException e) {
            LogUtils.e(e);
        }
    }

    public static String getErrorStackTrace(Throwable e) {
        StringBuilder sb = new StringBuilder();
        final Writer result = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(result);
        e.printStackTrace(printWriter);
        sb.append(result.toString());
        sb.append('\n');
        // If the exception was thrown in a background thread inside
        // AsyncTask, then the actual exception can be found with getCause
        Throwable cause = e.getCause();

        if (cause != null) {
            cause.printStackTrace(printWriter);
            sb.append("==== Root Cause ====\n");
            sb.append(result.toString());
            sb.append('\n');
        }

        printWriter.close();
        return sb.toString();
    }

}
