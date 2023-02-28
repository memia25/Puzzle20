package com.example.puzzle20;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;
import android.os.Build;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.annotation.TargetApi;

/**
 * author: Griselda Memia
 * description: creates a gridView that looks for touches and drag actions
 * date: 02/27/2022

 * https://developer.android.com/reference/android/widget/GridView
 * Everything on how to use GridLayout
 */

public class MyGridView extends GridView {

    private float locX; //x location of the event
    private float locY; //y location of the event
    private boolean validTouch=false; //boolean for the validity of the touch
    private GestureDetector touchSense; //touch sensor
    private static final int minDrag=100; //minimum distance touch must be dragged
    private static final int maxStray=100; //maximum distance a drag can stray
    private static final int dragSpeed=100; //minimum speed of drag


        public MyGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public MyGridView(Context context) {
        super(context);
        initialize(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP) //might need to change
    public MyGridView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initialize(context);
    }
    public MyGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }

    /**
     * @param event: drag event passed into this method
     * @return: returns a boolean if the drag action was valid or not
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        touchSense.onTouchEvent(event);

        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            validTouch = false;
        } else if (action == MotionEvent.ACTION_DOWN) {
            locX = event.getX();
            locY = event.getY();
        } else {
            if (validTouch) {
                return true;
            }
            float xDiff = (Math.abs(event.getX() - locX));
            float yDiff = (Math.abs(event.getY() - locY));
            if ((xDiff > minDrag) || (yDiff > minDrag)) {
                validTouch = true;
                return true;
            }
        }
        return super.onInterceptTouchEvent(event);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event){
        return touchSense.onTouchEvent(event);
    }

    private void initialize(final Context context) {
        touchSense = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {

            /**
             * This method measures changes in starting/ending places for a drag event and determines what direction the drag was
             * @param event1 start of drag
             * @param event2 end of drag
             * @param speedX how fast in the x direction the drag was
             * @param speedY how fast in the y direction the drag was
             * @return returns a boolean value after parent constructor is called
             */
            @Override
            public boolean onFling(MotionEvent event1, MotionEvent event2, float speedX, float speedY) {
                final int position = MyGridView.this.pointToPosition(Math.round(event1.getX()), Math.round(event1.getY()));

                if (Math.abs(event1.getY() - event2.getY()) > maxStray) {
                    if (Math.abs(event1.getX() - event2.getX()) > maxStray || Math.abs(speedY) < dragSpeed) {
                        return false;
                    }
                    if (event1.getY() - event2.getY() > minDrag) {
                        MainActivity.moveSquares(context, MainActivity.up, position);
                    }
                    else if (event2.getY() - event1.getY() > minDrag) {
                        MainActivity.moveSquares(context, MainActivity.down, position);
                    }
                } else {
                    if (Math.abs(speedX) < dragSpeed) {
                        return false;
                    }
                    if (event1.getX() - event2.getX() > minDrag) {
                        MainActivity.moveSquares(context, MainActivity.left, position);
                    } else if (event2.getX() - event1.getX() > minDrag) {
                        MainActivity.moveSquares(context, MainActivity.right, position);
                    }
                }

                return super.onFling(event1, event2, speedX, speedY);
            }


            @Override
            public boolean onDown(MotionEvent event) {
                return true;
            }
        });
    }

}
