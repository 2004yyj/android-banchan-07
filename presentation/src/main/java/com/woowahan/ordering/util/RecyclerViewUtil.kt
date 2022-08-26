package com.woowahan.ordering.util

import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.removeAllItemDecorations() {
    if (itemDecorationCount > 0)
        removeItemDecorationAt(0)
}