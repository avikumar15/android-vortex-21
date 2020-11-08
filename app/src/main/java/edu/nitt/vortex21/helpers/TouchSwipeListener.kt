package edu.nitt.vortex21.helpers

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.MotionEvent.*
import android.view.View
import java.lang.Math.abs

abstract class OnSwipeTouchListener(context: Context): View.OnTouchListener {

    private val gestureDetector:GestureDetector by lazy {
        GestureDetector(context, GestureListener())
    }

    private var STORY_PRESS_TIME = 0L
    private var STORY_LIMIT_TIME = 500L

    abstract fun onSwipeLeft()
    abstract fun onSwipeRight()
    abstract fun takeAction()
    abstract fun pauseStory()
    abstract fun resumeStory()

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
        Log.i("Event Type",p1.toString())
        return when(p1?.action) {
            ACTION_DOWN -> {
                STORY_PRESS_TIME = System.currentTimeMillis()
                false
            }
            ACTION_MOVE -> {
                pauseStory()
                false
            }
            ACTION_UP -> {
                if(!gestureDetector.onTouchEvent(p1)) {
                    val TIME_NOW = System.currentTimeMillis()
                    if (STORY_LIMIT_TIME > TIME_NOW - STORY_PRESS_TIME)
                        takeAction()
                    else
                        resumeStory()
                }
                true
            }
            else -> true
        }
    }

    inner class GestureListener: GestureDetector.SimpleOnGestureListener() {

        private val SWIPE_DISTANCE_THRESHOLD = 20
        private val SWIPE_VELOCITY_THRESHOLD = 20

        override fun onDown(e: MotionEvent?): Boolean {
            return true
        }

        override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float
        ): Boolean {
            val distanceX = kotlin.math.abs(e1!!.x - e2!!.x)
            val distanceY = kotlin.math.abs(e1.y - e2.y)

            Log.i("Swipe distance", "x: $distanceX, y: $distanceY")
            Log.i("Swipe velocity", "x: $velocityX, y: $velocityY")

            if(distanceX>distanceY && distanceX> SWIPE_DISTANCE_THRESHOLD && abs(velocityX)> SWIPE_VELOCITY_THRESHOLD) {
                if((e1.x - e2.x)>0)
                    onSwipeRight()
                else
                    onSwipeLeft()
                return true
            }
            return false
        }

    }
}
