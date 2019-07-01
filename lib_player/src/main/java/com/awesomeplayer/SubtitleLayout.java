package com.awesomeplayer;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by King Jocoa on 5/16/2017.
 */

public class SubtitleLayout extends LinearLayout implements GestureDetector.OnGestureListener {
    private static final String TAG = "SubtitleTextView";
    private GestureDetectorCompat mDetector;

    public SubtitleLayout(Context context) {
        super(context);
        init();
    }


    public SubtitleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SubtitleLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mDetector = new GestureDetectorCompat(getContext(), this);
        mDetector.setIsLongpressEnabled(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        this.mDetector.onTouchEvent(event);
        // Be sure to call the superclass implementation

        int action = MotionEventCompat.getActionMasked(event);

        switch(action) {

            case (MotionEvent.ACTION_UP) :
//                Log.d(TAG,"Action was UP");
                return true;
            default :
                return super.onTouchEvent(event);
        }
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
//        Log.d(TAG,"Action was onLongPress");

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }
}
