package com.woowahan.ordering.ui.listener

import android.view.View

class OnThrottleClickListener(
    private val onClickListener: View.OnClickListener,
    private val interval: Long = 300L
): View.OnClickListener {

    private var clickable = true

    override fun onClick(v: View?) {
        if (clickable) {
            clickable = false
            v?.run{
                postDelayed({
                    clickable = true
                }, interval)
                onClickListener.onClick(v)
            }
        }
    }
}

fun View.setOnThrottleClickListener(action: (v: View) -> Unit) {
    val listener = View.OnClickListener { action(it) }
    setOnClickListener(OnThrottleClickListener(listener))
}

fun View.setOnThrottleClickListener(action: (v: View) -> Unit, interval: Long) {
    val listener = View.OnClickListener { action(it) }
    setOnClickListener(OnThrottleClickListener(listener, interval))
}