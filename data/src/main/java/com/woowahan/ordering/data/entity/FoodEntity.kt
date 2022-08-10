package com.woowahan.ordering.data.entity

import com.google.gson.annotations.SerializedName

data class FoodEntity(
    @SerializedName("detail_hash")
    val detailHash: String,
    val image: String,
    val alt: String,
    @SerializedName("delivery_type")
    val deliveryType: List<String>,
    val title: String,
    val description: String,
    @SerializedName("n_price")
    val price: String?,
    @SerializedName("s_price")
    val discountedPrice: String,
    val badge: List<String>?
)