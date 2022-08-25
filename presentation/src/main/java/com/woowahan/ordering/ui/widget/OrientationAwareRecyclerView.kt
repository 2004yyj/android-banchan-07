package com.woowahan.ordering.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent.ACTION_UP
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs

class OrientationAwareRecyclerView(
    context: Context,
    attributeSet: AttributeSet
) : RecyclerView(context, attributeSet) {

    private var lastX = 0.0f
    private var lastY = 0.0f
    private var scrolling = false

    init {
        addOnScrollListener(object : OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                scrolling = newState != SCROLL_STATE_IDLE
            }
        })
    }

    override fun onInterceptTouchEvent(e: MotionEvent?): Boolean {
        val layoutManager = layoutManager ?: return super.onInterceptTouchEvent(e)
        var allowScroll = true

        when (e?.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                lastX = e.x
                lastY = e.y
                if (scrolling) {
                    val newEvent = MotionEvent.obtain(e)
                    newEvent.action = ACTION_UP
                    return super.onInterceptTouchEvent(newEvent)
                }
            }

            MotionEvent.ACTION_MOVE -> {
                val currentX = e.x
                val currentY = e.y
                val dx = abs(currentX - lastX)
                val dy = abs(currentY - lastY)
                allowScroll =
                    if (dy > dx)
                        layoutManager.canScrollVertically()
                    else
                        layoutManager.canScrollHorizontally()

                parent.requestDisallowInterceptTouchEvent(true)
            }
        }

        if (!allowScroll) {
            return false
        }

        return super.onInterceptTouchEvent(e)
    }
}