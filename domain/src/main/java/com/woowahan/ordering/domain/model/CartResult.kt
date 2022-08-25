package com.woowahan.ordering.domain.model

data class CartResult(
    val title: String,
    val count: Int,
    val isSelectedAll: Boolean,
    val list: List<Cart>,
    val sum: Long,
    val deliveryFee: Int,
    val insufficientAmount: Int,
    val enableToOrder: Boolean,
    val historyList : List<History> = listOf()
)