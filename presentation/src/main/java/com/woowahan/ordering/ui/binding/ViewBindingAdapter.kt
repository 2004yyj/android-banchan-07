package com.woowahan.ordering.ui.binding

import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.databinding.BindingAdapter
import com.woowahan.ordering.ui.listener.setOnThrottleClickListener

@BindingAdapter("match_parent", "width")
fun View.setLayoutWidth(matchParent: Boolean, dimen: Float) {
    val layoutParams = this.layoutParams
    if (!matchParent) {
        layoutParams.width = dimen.toInt()
        this.layoutParams = layoutParams
    } else {
        layoutParams.width = MATCH_PARENT
        this.layoutParams = layoutParams
    }
}

@BindingAdapter("android:onThrottleClick")
fun View.setOnThrottleClick(
    listener: View.OnClickListener
) {
    setOnThrottleClickListener {
        listener.onClick(it)
    }
}