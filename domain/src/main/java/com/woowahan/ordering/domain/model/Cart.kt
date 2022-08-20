package com.woowahan.ordering.domain.model

data class Cart(
    val id: Int = 0,
    val title: String,
    val thumbnail: String,
    val discountedRate: Int = 0,
    val originalPrice: Long = 0,
    val discountedPrice: Long,
    val count: Int = 1,
    val detailHash: String,
    val isChecked: Boolean = true
)