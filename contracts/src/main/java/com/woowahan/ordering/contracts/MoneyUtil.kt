package com.woowahan.ordering.contracts

fun String.toMoneyLong(): Long {
    return this.replace("[^0-9]".toRegex(), "").toLong()
}