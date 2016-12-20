package com.xxz.magnet.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.lang.reflect.Field;

/**
 * View处理
 * Created with Android Studio.
 * <p/>
 * Author:xiaxf
 * <p/>
 * Date:2015/7/16.
 */
public class ViewUtils {
    // 标准UI设计宽度（px）
    private static final float UI_DESIGN_ORIENTATION_LANDSCAPE_WIDTH = 1920.0f;
    private static final float UI_DESIGN_ORIENTATION_PORTRAIT_WIDTH = 1080.0f;
    // 缩放比例
    public static float SCALE = 0;

    /**
     * 设置View背景
     *
     * @param view
     * @param background
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void setViewBackground(View view, Drawable background) {
        if (SDKCompat.hasJellyBean()) {
            view.setBackground(background);
        } else {
            view.setBackgroundDrawable(background);
        }
    }

    public static void setViewBackground(View view, int drawableId) {
        Drawable drawable = view.getContext().getResources().getDrawable(drawableId);
        setViewBackground(view, drawable);
    }

    /**
     * 判断view是否显示
     *
     * @param view
     * @return
     */
    public static boolean isVisible(View view) {
        return view.getVisibility() == View.VISIBLE;
    }

    /**
     * 设置View为Visible
     *
     * @param view
     */
    public static void setViewVisible(View view) {
        view.setVisibility(View.VISIBLE);
    }

    /**
     * 设置View为Gone
     *
     * @param view
     */
    public static void setViewGone(View view) {
        view.setVisibility(View.GONE);
    }

    /**
     * 设置View为Invisible
     *
     * @param view
     */
    public static void setViewInvisible(View view) {
        view.setVisibility(View.INVISIBLE);
    }

    /***
     * 释放ImageView图片资源
     *
     * @param imageView
     */
    public static void releaseImageDrawable(ImageView imageView) {
        if (imageView == null)
            return;
        Drawable d = imageView.getDrawable();
        if (d != null)
            d.setCallback(null);
        imageView.setImageDrawable(null);
        ViewUtils.setViewBackground(imageView, null);
    }

    /**
     * 释放view背景资源
     *
     * @param view
     */
    public static void releaseLayoutDrawable(View view) {
        if (view == null)
            return;
        Drawable d = view.getBackground();
        if (d != null)
            d.setCallback(null);
        ViewUtils.setViewBackground(view, null);
    }

    /**
     * findViewById 省略强转过程
     *
     * @param activity
     * @param resId
     * @return
     */
    public static <V> V findView(Activity activity, @IdRes int resId) {
        //noinspection unchecked
        return (V) activity.findViewById(resId);
    }

    /**
     * findViewById 省略强转过程
     *
     * @param resId
     * @param rootView
     * @param <V>具体的View类型
     * @return
     */
    @IdRes
    public static <V> V findView(View rootView, @IdRes int resId) {
        //noinspection unchecked
        return (V) rootView.findViewById(resId);
    }

    /**
     * findviewById 并添加点击事件
     *
     * @param activity
     * @param resId
     * @param onClickListener
     * @param <V>具体的View类型
     * @return
     */
    public static <V> V findViewAttachOnclick(Activity activity, @IdRes int resId, View.OnClickListener onClickListener) {
        View view = activity.findViewById(resId);
        view.setOnClickListener(onClickListener);
        //noinspection unchecked
        return (V) view;
    }

    /**
     * findviewById 并添加点击事件
     *
     * @param rootView
     * @param resId
     * @param onClickListener
     * @param <V>具体的View类型
     * @return
     */
    @IdRes
    public static <V> V findViewAttachOnclick(View rootView, @IdRes int resId, View.OnClickListener onClickListener) {
        //noinspection unchecked
        View view = rootView.findViewById(resId);
        view.setOnClickListener(onClickListener);
        //noinspection unchecked
        return (V) view;
    }


    /**
     * ListView 是否滚动到顶部
     *
     * @param listView
     * @return
     */
    public static boolean isScrollTop(ListView listView) {
        if (listView != null && listView.getChildCount() > 0) {
            if (listView.getChildAt(0).getTop() < 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * ScrollView 是否滚动到顶部
     *
     * @param scrollView
     * @return
     */
    public static boolean isScrollTop(ScrollView scrollView) {
        if (scrollView != null) {
            if (scrollView.getScrollY() > 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 控制view的visible与Gone
     *
     * @param view
     */
    public static void toggleView(View view) {
        if (view.getVisibility() == View.GONE) {
            setViewVisible(view);
        } else if (view.getVisibility() == View.VISIBLE) {
            setViewGone(view);
        }
    }



    /**
     * 设置TextView的Drawable （适用于Button, EditText, RadioButton等任意继承TextView的view）
     * @param textView textView
     * @param gravity Drawable位置
     * @param drawablePadding Drawable与文字的间距,如果是-1则不设置间距
     * @param drawableWidth Drawable宽
     * @param drawableHight Drawable高
     * @param drawableRes Drawable资源
     */
    public static void setTextViewDrawable(TextView textView, int gravity, @DimenRes int drawablePadding, @DimenRes int drawableWidth,
                                           @DimenRes int drawableHight, @DrawableRes int drawableRes) {
        Drawable drawable = getDrawableForSize(textView.getContext(), drawableWidth, drawableHight, drawableRes);
        if (drawable == null)
            return;
        if (drawablePadding >= 0) {
            int padding = ((int) textView.getContext().getResources().getDimension(drawablePadding));
            textView.setCompoundDrawablePadding(padding);
        }

        switch (gravity){
            case Gravity.LEFT:
                textView.setCompoundDrawables(drawable, null, null, null);
                break;

            case Gravity.TOP:
                textView.setCompoundDrawables(null, drawable, null, null);
                break;

            case Gravity.RIGHT:
                textView.setCompoundDrawables(null, null, drawable, null);
                break;

            case Gravity.BOTTOM:
                textView.setCompoundDrawables(null, null, null, drawable);
                break;
        }
    }

    public static Drawable getDrawableForSize(Context context, @DimenRes int drawableWidth, @DimenRes int drawableHight,
                                              @DrawableRes int drawableRes) {
        Drawable drawable = context.getResources().getDrawable(drawableRes);
        if (drawable == null)
            return null;
        int width = ((int) context.getResources().getDimension(drawableWidth));
        int hight = ((int) context.getResources().getDimension(drawableHight));
        drawable.setBounds(0, 0, width, hight);
        return drawable;
    }

}
