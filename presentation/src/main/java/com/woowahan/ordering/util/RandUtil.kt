package com.woowahan.ordering.util

import java.security.SecureRandom

fun executeRandom(): Int {
    val secureRandom = SecureRandom()
    return secureRandom.nextInt(Int.MAX_VALUE)
}