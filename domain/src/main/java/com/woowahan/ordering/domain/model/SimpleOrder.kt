package com.woowahan.ordering.domain.model

data class SimpleOrder(
    val name: String,
    val deliveryTime: Long,
    val thumbnail: String,
    val totalPrice: Long,
    val productCount: Int,
)