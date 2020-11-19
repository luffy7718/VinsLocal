package com.lekcie.vinslocal.Utils;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.lekcie.vinslocal.R;

public class RangeBar extends View {

    private int max;
    private int progressStart;
    private int progressEnd;
    private int color;

    private float thumbRadius;
    private float barHeight;

    private Bar bar;
    private ConnectingThumb connectingThumb;
    private boolean isRange;

    private OnRangeChangeListener onRangeChangeListener;

    public RangeBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public void setOnRangeChangeListener(OnRangeChangeListener onRangeChangeListener) {
        this.onRangeChangeListener = onRangeChangeListener;
    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RangeBar, defStyleAttr, 0);

        max = a.getInteger(R.styleable.RangeBar_max, 100);
        try {
            color = a.getColor(R.styleable.RangeBar_rangeColor, getDefaultRangeColor());
        } catch (Exception e) {
            color = getDefaultRangeColor();
            e.printStackTrace();
        }

        thumbRadius = a.getDimension(R.styleable.RangeBar_thumbRadius, dpToPx(8));
        barHeight = a.getDimension(R.styleable.RangeBar_barHeight, dpToPx(3));

        if (max < 3) {
            max = 3;
        }

        progressStart = a.getInteger(R.styleable.RangeBar_progressStart, 0);
        progressEnd = a.getInteger(R.styleable.RangeBar_progressEnd, max);
        isRange = a.getBoolean(R.styleable.RangeBar_isRange, true);

        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int desiredWidth = 80;
        int desiredHeight = 40 + getPaddingTop() + getPaddingBottom();

        if (Math.max(thumbRadius * 2, barHeight) > desiredHeight) {
            desiredHeight = (int) Math.max(thumbRadius * 2f, barHeight);
        }

        if (Math.max(thumbRadius * 2, barHeight) > desiredWidth) {
            desiredWidth = (int) Math.max(thumbRadius * 2f, barHeight);
        }

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        //Widget Width
        if (widthMode == MeasureSpec.EXACTLY) {
            //Must be this size
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            width = Math.min(desiredWidth, widthSize);
        } else {
            //Be whatever you want
            width = desiredWidth;
        }

        //Widget Height
        if (heightMode == MeasureSpec.EXACTLY) {
            //Must be this size
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            height = Math.min(desiredHeight, heightSize);
        } else {
            //Be whatever you want
            height = desiredHeight;
        }

        //MUST CALL THIS
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (bar != null) bar.show(canvas);
        if (connectingThumb != null) {
            if(isEnabled()){
                connectingThumb.setRangeColor(color);
            }else{
                connectingThumb.setRangeColor(Color.GRAY);
            }
            connectingThumb.show(canvas);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        bar = new Bar(Color.LTGRAY, barHeight, w, h, thumbRadius);

        if(isEnabled()) {
            connectingThumb = new ConnectingThumb(color, barHeight, w, h, thumbRadius, max);
        }else{
            connectingThumb = new ConnectingThumb(Color.GRAY, barHeight, w, h, thumbRadius, max);
        }
        connectingThumb.setRangeEnabled(isRange);

        connectingThumb.setProgressStart(progressStart);
        connectingThumb.setProgressEnd(progressEnd);
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
        if (max < 3) {
            max = 3;
        }
        if (connectingThumb != null) connectingThumb.setMax(max);
        invalidate();
    }

    public int getProgressStart() {
        return Math.min(progressStart, progressEnd);
    }

    public void setProgressStart(int progressStart) {
        //always progress is greater so change end progress is start progress is greater
        if (progressStart > progressEnd) {
            setProgressEnd(progressStart);
            progressStart = progressEnd;
        }
        if (progressStart < 0) {
            progressStart = 0;
        } else if (progressStart > max) {
            progressStart = max;
        }

        this.progressStart = progressStart;
        if (startIndexPrev != progressStart && connectingThumb != null) {
            connectingThumb.setProgressStart(progressStart);
            if (onRangeChangeListener != null) {
                onRangeChangeListener.onRangeChanged(this, Math.min(progressStart, progressEnd), Math.max(progressStart, progressEnd), false);
            }
            startIndexPrev = progressStart;
            invalidate();
        }
    }

    public int getProgressEnd() {
        return Math.max(progressStart, progressEnd);
    }

    public void setProgressEnd(int progressEnd) {
        //always progress is greater so change end progress is start progress is greater
        if (progressEnd < progressStart) {
            setProgressStart(progressEnd);
            progressEnd = progressStart;
        }
        if (progressEnd < 0) {
            progressEnd = 0;
        } else if (progressEnd > max) {
            progressEnd = max;
        }
        this.progressEnd = progressEnd;
        if (endIndexPrev != progressEnd && connectingThumb != null) {
            connectingThumb.setProgressEnd(progressEnd);
            if (onRangeChangeListener != null) {
                onRangeChangeListener.onRangeChanged(this, Math.min(progressStart, progressEnd), Math.max(progressStart, progressEnd), false);
            }
            endIndexPrev = progressEnd;
            invalidate();
        }
        invalidate();

    }

    int startIndexPrev = -1;
    int endIndexPrev = -1;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnabled()) {
            return super.onTouchEvent(event);
        }
        float x = event.getX();

        Log.d("biky", "touch action = " + event.getAction());

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (connectingThumb != null) {
                    connectingThumb.press(x);
                    connectingThumb.update(x);
                }
                if (onRangeChangeListener != null) {
                    onRangeChangeListener.onStartTrackingTouch(this, Math.min(progressStart, progressEnd), Math.max(progressStart, progressEnd));
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (connectingThumb != null) connectingThumb.update(x);
                break;
            case MotionEvent.ACTION_UP:
                if (connectingThumb != null) connectingThumb.release();
                if (onRangeChangeListener != null) {
                    onRangeChangeListener.onStopTrackingTouch(this, Math.min(progressStart, progressEnd), Math.max(progressStart, progressEnd));
                }
                break;
            default:
                return super.onTouchEvent(event);
        }

        if (connectingThumb != null) {
            progressStart = connectingThumb.getProgressStart();
            progressEnd = connectingThumb.getProgressEnd();
        }

        if (progressStart != startIndexPrev || progressEnd != endIndexPrev) {
            if (onRangeChangeListener != null) {
                onRangeChangeListener.onRangeChanged(this, Math.min(progressStart, progressEnd), Math.max(progressStart, progressEnd), true);
            }
        }

        startIndexPrev = progressStart;
        endIndexPrev = progressEnd;

        invalidate();
        return true;
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (getParent() != null && event.getAction() == MotionEvent.ACTION_DOWN) {
            getParent().requestDisallowInterceptTouchEvent(true);
        }
        return super.dispatchTouchEvent(event);
    }

    private int getDefaultRangeColor() {
        TypedValue typedValue = new TypedValue();

        TypedArray a = getContext().obtainStyledAttributes(typedValue.data, new int[]{R.attr.colorAccent});

        int color;
        try {
            color = a.getColor(0, Color.parseColor("#FF4081"));
        } catch (Exception e) {
            // pink accent
            color = Color.parseColor("#FF4081");
        }

        a.recycle();

        return color;
    }
}