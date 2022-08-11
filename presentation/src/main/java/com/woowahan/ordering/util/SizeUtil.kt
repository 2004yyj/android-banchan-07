package com.woowahan.ordering.util

import android.content.res.Resources

fun Int.toPx(resources: Resources): Int {
    return (this * resources.displayMetrics.density).toInt()
}

fun Int.toDp(resources: Resources): Int {
    return (this / resources.displayMetrics.density).toInt()
}