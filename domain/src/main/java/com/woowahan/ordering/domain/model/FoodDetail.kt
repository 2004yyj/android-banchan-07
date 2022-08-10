package com.woowahan.ordering.domain.model

data class FoodDetail(
    val topImage: String,
    val thumbImages: List<String>,
    val productDescription: String,
    val point: Long,
    val deliveryInfo: String,
    val deliveryFee: String,
    val prices: List<Long>,
    val detailSection: List<String>
)