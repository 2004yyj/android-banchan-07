package com.woowahan.ordering.data.util

fun String.toMoneyLong(): Long {
    return this.replace("[^0-9]".toRegex(), "").toLong()
}