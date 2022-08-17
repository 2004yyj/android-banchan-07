package com.woowahan.ordering.contracts

fun Long.getDiffFromNow(): String {
    val now = System.currentTimeMillis()
    val diff = now - this

    val seconds = diff / 1000
    val minutes = seconds / 60
    val hours = minutes / 60
    val days = hours / 24

    if (days >= 365) return "${days / 365}년전"
    if (days >= 30) return "${days / 30}달전"
    if (days > 0) return "${days}일전"
    if (hours > 0) return "${hours}시간전"
    if (minutes > 0) return "${minutes}분전"
    return "${seconds}초전"
}