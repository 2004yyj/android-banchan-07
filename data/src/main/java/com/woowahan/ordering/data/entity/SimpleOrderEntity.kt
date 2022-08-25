package com.woowahan.ordering.data.entity

data class SimpleOrderEntity(
    val title: String,
    val deliveryTime: Long,
    val thumbnail: String,
    val totalPrice: Long,
    val productCount: Int,
)
