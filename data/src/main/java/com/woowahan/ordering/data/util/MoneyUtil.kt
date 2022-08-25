package com.woowahan.ordering.data.util

fun String.toMoneyLong(): Long {
    return this.replace("[^0-9]".toRegex(), "").toLong()
}

fun Long.getDiscountRate(original: Long): Int {
    return if (original == 0L) 0
    else ((1 - (this.toFloat() / original.toFloat())) * 100).toInt()
}