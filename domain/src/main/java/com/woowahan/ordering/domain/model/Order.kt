package com.woowahan.ordering.domain.model

data class Order(
    val id: Int,
    val deliveryTime: Long,
    val isDelivered: Boolean = false
)