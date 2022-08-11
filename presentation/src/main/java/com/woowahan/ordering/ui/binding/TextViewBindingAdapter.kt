package com.woowahan.ordering.ui.binding

import android.graphics.Paint
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import coil.load

@BindingAdapter("android:lineThrough")
fun TextView.setTextLineThrough(boolean: Boolean) {
    paintFlags = if (boolean) {
        paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
    } else {
        paintFlags xor Paint.STRIKE_THRU_TEXT_FLAG
    }
}