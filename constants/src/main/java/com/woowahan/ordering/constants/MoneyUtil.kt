package com.woowahan.ordering.constants

fun String.toMoneyLong(): Long {
    return this.replace("[^0-9]".toRegex(), "").toLong()
}