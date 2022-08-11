package com.woowahan.ordering.ui.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load

@BindingAdapter("android:image")
fun ImageView.setImage(image: Any) {
    load(image)
}