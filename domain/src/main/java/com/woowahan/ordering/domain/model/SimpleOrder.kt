package com.woowahan.ordering.domain.model

data class SimpleOrder(
    val title: String,
    val deliveryTime: Long,
    val thumbnail: String,
    val totalPrice: Long,
    val productCount: Int,
    val isDelivered: Boolean
)