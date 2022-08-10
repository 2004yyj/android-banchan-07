package com.woowahan.ordering.domain.model

data class Food(
    val detailHash: String,
    val image: String,
    val alt: String,
    val deliveryType: List<String>,
    val title: String,
    val description: String,
    val price: Long,
    val discountedPrice: Long,
    val badge: List<String>,
    var discountRate: Int = 0,
    var isAdded: Boolean = false
)