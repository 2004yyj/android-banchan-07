package com.woowahan.ordering.ui.binding

import android.graphics.Paint
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.woowahan.ordering.contracts.getDiffFromNow

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

@BindingAdapter("app:deliveryTime", "app:deliveringColor", "app:deliveredColor")
fun TextView.setDeliveryTime(timestamp: Long, deliveringColor: Int, deliveredColor: Int) {
    val now = System.currentTimeMillis()
    text = if (timestamp > now) "배송 준비중" else "배송완료"
    setTextColor(if (timestamp > now) deliveringColor else deliveredColor)
}