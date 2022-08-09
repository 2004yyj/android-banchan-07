package com.woowahan.ordering.data.entity

data class SimpleOrderEntity(
    val name: String,
    val deliveryTime: Long,
    val thumbnail: String,
    val totalPrice: Long,
    val productCount: Int,
)
