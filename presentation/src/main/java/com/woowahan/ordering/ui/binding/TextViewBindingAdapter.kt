package com.woowahan.ordering.ui.binding

import android.graphics.Paint
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.woowahan.ordering.util.getDiffFromNow
import com.woowahan.ordering.util.isTimeout

@BindingAdapter("android:lineThrough")
fun TextView.setTextLineThrough(boolean: Boolean) {
    paintFlags = if (boolean) {
        paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
    } else {
        paintFlags xor Paint.STRIKE_THRU_TEXT_FLAG
    }
}

@BindingAdapter("android:diffTimeStamp")
fun TextView.setDiffTimeStamp(timestamp: Long) {
    text = timestamp.getDiffFromNow()
}

@BindingAdapter("android:isTimeout")
fun TextView.setTimestampVisibility(timestamp: Long) {
    isVisible = timestamp.isTimeout()
}