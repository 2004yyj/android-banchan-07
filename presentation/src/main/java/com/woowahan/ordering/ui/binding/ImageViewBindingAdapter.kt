package com.woowahan.ordering.ui.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import com.woowahan.ordering.R

@BindingAdapter("android:image")
fun ImageView.setImage(image: Any) {
    load(image) {
        placeholder(R.drawable.ic_placeholder)
    }
}