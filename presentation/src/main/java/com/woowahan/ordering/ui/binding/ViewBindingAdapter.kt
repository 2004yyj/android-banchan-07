package com.woowahan.ordering.ui.binding

import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.databinding.BindingAdapter
import com.woowahan.ordering.ui.listener.setOnThrottleClickListener

@BindingAdapter("android:onThrottleClick")
fun View.setOnThrottleClick(
    listener: View.OnClickListener
) {
    setOnThrottleClickListener {
        listener.onClick(it)
    }
}