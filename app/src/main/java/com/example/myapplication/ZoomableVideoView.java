package com.example.myapplication;

import android.content.Context;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.VideoView;

import android.content.Context;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.VideoView;

public class ZoomableVideoView extends VideoView implements ScaleGestureDetector.OnScaleGestureListener {

    private ScaleGestureDetector mScaleGestureDetector;
    private float mScaleFactor = 1.0f;
    private float MIN_SCALE_FACTOR = 1.0f;
    private float MAX_SCALE_FACTOR = 3.0f;

    public ZoomableVideoView(Context context) {
        super(context);
        init(context);
    }

    public ZoomableVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ZoomableVideoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        mScaleGestureDetector = new ScaleGestureDetector(context, this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mScaleGestureDetector.onTouchEvent(event);
        return true;
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        mScaleFactor *= detector.getScaleFactor();
        mScaleFactor = Math.max(MIN_SCALE_FACTOR, Math.min(mScaleFactor, MAX_SCALE_FACTOR));

        setScaleX(mScaleFactor);
        setScaleY(mScaleFactor);

        return true;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {
    }

    public void setMinScale(float minScale) {
        this.MIN_SCALE_FACTOR = minScale;
    }

    public void setMaxScale(float maxScale) {
        this.MAX_SCALE_FACTOR = maxScale;
    }
}
