package com.woowahan.ordering.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs

class HorizontalRecyclerView(
    context: Context,
    attributeSet: AttributeSet
) : RecyclerView(context, attributeSet) {

    init {
        addOnItemTouchListener(object : OnItemTouchListener {
            private var lastX = 0.0f
            private var lastY = 0.0f
            private var isLastItem = false
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                val layoutManager = layoutManager as LinearLayoutManager?
                when (e.action) {
                    MotionEvent.ACTION_DOWN -> {
                        lastX = e.x
                        lastY = e.y
                        isLastItem =
                            layoutManager?.findLastCompletelyVisibleItemPosition() == adapter!!.itemCount - 1
                    }
                    MotionEvent.ACTION_MOVE -> {
                        val currentX = e.x
                        val dx = abs(lastX - e.x)
                        val dy = abs(lastY - e.y)
                        if (dy > dx) {
                            parent.requestDisallowInterceptTouchEvent(false)
                            return false
                        }
                        if (lastX < currentX) {
                            parent.requestDisallowInterceptTouchEvent(true)
                        } else {
                            parent.requestDisallowInterceptTouchEvent(!isLastItem)
                        }
                    }
                }
                return false
            }

            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}
            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}
        })
    }
}