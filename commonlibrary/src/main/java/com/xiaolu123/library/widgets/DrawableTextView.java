package com.xiaolu123.library.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;
import com.xiaolu123.library.R;

/**
 * 可设置Drawable大小的TextView
 * Created with Android Studio
 * </p>
 * Authour:xiaxf
 * </p>
 * Date:16/1/22.
 */

public class DrawableTextView extends TextView {
    private int drawableWidth;
    private int drawableHight;
    private int gravity;

    public DrawableTextView(Context context) {
        this(context, null, 0);
    }

    public DrawableTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DrawableTextView);
        if (typedArray == null) {
            return;
        }
        try {
             drawableWidth = ((int) typedArray.getDimension(R.styleable.DrawableTextView_drawableWidth, -1));
             drawableHight = ((int) typedArray.getDimension(R.styleable.DrawableTextView_drawableHight, -1));

            if (drawableWidth < 0 || drawableHight < 0) {
                return;
            }

            Drawable drawables[] = this.getCompoundDrawables();
            Drawable drawable;
            if (drawables != null) {
                drawable = drawables[0];
                if (drawable != null) {
                    drawable.setBounds(0, 0, drawableWidth, drawableHight);
                    setCompoundDrawables(drawable, null, null, null);
                    return;
                }

                drawable = drawables[1];
                if (drawable != null) {
                    drawable.setBounds(0, 0, drawableWidth, drawableHight);
                    setCompoundDrawables(null, drawable, null, null);

                    return;
                }

                drawable = drawables[2];
                if (drawable != null) {
                    drawable.setBounds(0, 0, drawableWidth, drawableHight);
                    setCompoundDrawables(null, null, drawable, null);

                    return;
                }

                drawable = drawables[3];
                if (drawable != null) {
                    drawable.setBounds(0, 0, drawableWidth, drawableHight);
                    setCompoundDrawables(null, null, null, drawable);
                }
            }

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            typedArray.recycle();
        }

    }

    public void setDrawable(int gravity, @DrawableRes int drawableRes) {
        Drawable drawable = this.getResources().getDrawable(drawableRes);
        if (drawable == null)
            return;
        if (drawableWidth == 0 || drawableHight == 0)
            return;

        drawable.setBounds(0, 0, drawableWidth, drawableHight);
        switch (gravity) {
            case Gravity.LEFT:
                setCompoundDrawables(drawable, null, null, null);
                break;

            case Gravity.TOP:
                setCompoundDrawables(null, drawable, null, null);
                break;

            case Gravity.RIGHT:
                setCompoundDrawables(null, null, drawable, null);
                break;

            case Gravity.BOTTOM:
                setCompoundDrawables(null, null, null, drawable);
                break;
        }
    }
}
