package com.freddy.stickylistheaderswithsidebardemo.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.freddy.stickylistheaderswithsidebardemo.R;

/**
 * Description:
 * Created by freddy on 16/5/30.
 */
public class SideBar extends View {
    private Context context;
    private Paint textPaint = new Paint();

    private static final int DEFAULT_WIDTH = 30;
    private static final int DEFAULT_HEIGHT = 100;

    private int width;
    private int height;

    private String[] values;

    private int choose = -1;//选中状态

    private OnTouchLetterChangedListener onTouchLetterChangedListener;

    public SideBar(Context context) {
        super(context);
        this.context = context;
    }

    public SideBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public SideBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    public void setValues(String[] values) {
        this.values = values;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        measureWidth(widthMode, widthSize);
        measureHeight();
        setMeasuredDimension(width, height);
    }

    private void measureWidth(int widthMode, int widthSize){
        if (widthMode == MeasureSpec.EXACTLY){
            width = widthSize;
        }else if (widthMode == MeasureSpec.AT_MOST){
            width = (int) px2dp(DEFAULT_WIDTH);
        }
    }

    private void measureHeight(){
        if (values == null){
            height = (int) px2dp(DEFAULT_HEIGHT);
        }else{
            Rect rect = new Rect();
            for (int i = 0; i < values.length; i++) {
                textPaint.getTextBounds(values[i], 0, values[i].length(), rect);
                height += px2dp(rect.height());
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (values != null && values.length > 1){
            int singleHeight = height / values.length;
            for (int i = 0; i < values.length; i++) {
                textPaint.setColor(Color.rgb(33, 65, 98));
                textPaint.setTypeface(Typeface.DEFAULT_BOLD);
                textPaint.setAntiAlias(true);
                textPaint.setTextSize(20);
                if (i == choose){//选中改变字体颜色
                    textPaint.setColor(Color.parseColor("#3399ff"));
                    textPaint.setFakeBoldText(true);
                }

                float posX = width/2 - textPaint.measureText(values[i])/2;
                float posY = singleHeight * i + singleHeight;
                canvas.drawText(values[i], posX, posY, textPaint);
                textPaint.reset();//重置画笔
            }
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        final float touchY = event.getY(); //触摸的y轴位置
        final int oldChoose = choose;

        final int position = (int) (touchY / height * values.length);
        switch (action){
            case MotionEvent.ACTION_UP:
                setBackground(new ColorDrawable(0x00000000));//将背景设为透明
                choose = -1;
                invalidate();
                break;
            default:
                setBackgroundResource(R.drawable.sidebar_background);
                if (oldChoose != position){
                    if (position >=0 && position < values.length){
                        if (onTouchLetterChangedListener != null){
                            onTouchLetterChangedListener.onLetterChanged(values[position]);
                        }
                    }
                    choose = position;
                    invalidate();
                }
                break;
        }

        return true;
    }

    private float px2dp(int px){
        float scale = context.getResources().getDisplayMetrics().density;
        return scale * px + 0.5f;
    }

    public interface OnTouchLetterChangedListener{
        void onLetterChanged(String str);
    }

    public void setOnTouchLetterChangedListener(OnTouchLetterChangedListener onTouchLetterChangedListener) {
        if (onTouchLetterChangedListener != null){
            this.onTouchLetterChangedListener = onTouchLetterChangedListener;
        }
    }
}
