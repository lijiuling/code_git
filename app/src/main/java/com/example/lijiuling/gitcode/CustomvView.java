package com.example.lijiuling.gitcode;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;


/**
 * Created by lijiuling on 2017/8/6.
 */

public class CustomvView extends View implements View.OnClickListener {

    public static final String TAG = "CustomView";
    /*主题文字*/
    private String titleText;
    /*主题颜色*/
    private int titleColor;
    /*主题大小*/
    private int titleSize;
    /*绘制时控制文本的大小和范围*/
    private Rect mBound;
    /*绘制时用的画笔*/
    private Paint mPaint;

    public CustomvView(Context context) {
        this(context, null);
        Log.e(TAG, "CustomvView 构造函数1");
    }

    public CustomvView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
        Log.e(TAG, "CustomvView 构造函数2");
    }

    public CustomvView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.e(TAG, "CustomvView 构造函数3");
        /*获得自定属性值*/
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomTitleView, defStyleAttr, 0);
        int tyleCount = a.getIndexCount();
        for (int i = 0; i < tyleCount; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.CustomTitleView_textTitle:
                    titleText = a.getString(attr);
                    break;
                case R.styleable.CustomTitleView_textTitleColor:
                    //设置默认颜色为黑色
                    titleColor = a.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.CustomTitleView_textTitleSize:
                    //将16sp装换成px
                    titleSize = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
                    break;
            }
        }
        a.recycle();
        /*获得绘制文本的宽高*/
        mPaint = new Paint();
        mPaint.setTextSize(titleSize);
        mBound = new Rect();
        mPaint.getTextBounds(titleText, 0, titleText.length(), mBound);
        setOnClickListener(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            mPaint.setTextSize(titleSize);
            mPaint.getTextBounds(titleText, 0, titleText.length(), mBound);
            width = mBound.width() + getPaddingLeft() + getPaddingRight();
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;

        } else {
            mPaint.setTextSize(titleSize);
            mPaint.getTextBounds(titleText, 0, titleText.length(), mBound);
            height = mBound.height() + getPaddingTop() + getPaddingBottom();
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.YELLOW);
        //canvas.drawRect(mBound,mPaint);
        Log.d(TAG, "canvas width = " + getWidth() + ", height = " + getHeight());
        canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);
        mPaint.setColor(titleColor);
        Log.e(TAG, "getWidth() / 2 = " + (getWidth() / 2) + "，mBound.width() / 2 = " + (mBound.width() / 2) + "，getHeight() / 2 = " + (getHeight() / 2) + ", mBound.height() / 2 = " + (mBound.height() / 2));
        /*注意此处宽度还需减去mBound.left的长度，坐标系是正常坐标*/
        canvas.drawText(titleText, getWidth() / 2 - mBound.width() / 2 - mBound.left , getHeight() / 2 + mBound.height() / 2, mPaint);
    }

    @Override
    public void onClick(View view) {
        titleText = "";
        titleText = createRandom();
        postInvalidate();
        requestLayout();
    }

    private String createRandom() {
        Random random = new Random();
        Set<Integer> set = new HashSet<Integer>();
        while (set.size() < 4) {
            set.add(random.nextInt(10));
        }
        StringBuffer str = new StringBuffer();
        for (int i : set) {
            str.append(i);
        }
        Log.d(TAG,"code = "+str);
        return str.toString();
    }
}
