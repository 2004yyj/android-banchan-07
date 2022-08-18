package com.woowahan.ordering.contracts

fun Long.getDiffFromNow(): String {
    val now = System.currentTimeMillis()
    var diff = now - this
    val isNegative = diff < 0

    if (isNegative) diff = -diff

    val seconds = diff / 1000
    val minutes = seconds / 60
    val hours = minutes / 60
    val days = hours / 24

    val result = if (days >= 365) "${days / 365}년"
    else if (days >= 30) "${days / 30}달"
    else if (days > 0) "${days}일"
    else if (hours > 0) "${hours}시간"
    else if (minutes > 0) "${minutes}분"
    else "${seconds}초"

    return if (isNegative) result else "$result 전"
}

fun Long.isTimeout(): Boolean {
    val currentTime = System.currentTimeMillis()
    return currentTime - this < 0
}