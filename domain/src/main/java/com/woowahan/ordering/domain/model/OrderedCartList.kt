package com.woowahan.ordering.domain.model

data class OrderedCartList(
    val count: Int = 0,
    val sumOfPrice: Long = 0,
    val isNeedDeliveryFee: Boolean = false,
    val list: List<Cart> = emptyList()
)