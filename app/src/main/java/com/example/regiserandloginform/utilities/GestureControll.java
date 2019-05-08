package com.example.regiserandloginform.utilities;

import android.view.GestureDetector;
import android.view.MotionEvent;

public class GestureControll extends GestureDetector.SimpleOnGestureListener {

    @Override
    public void onLongPress(MotionEvent event) {
        // triggers after onDown only for long press
        super.onLongPress(event);
    }
}
