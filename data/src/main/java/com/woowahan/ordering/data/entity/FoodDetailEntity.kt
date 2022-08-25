package com.woowahan.ordering.data.entity

import com.google.gson.annotations.SerializedName

data class FoodDetailEntity(
    @SerializedName("top_image")
    val topImage: String,
    @SerializedName("thumb_images")
    val thumbImages: List<String>,
    @SerializedName("product_description")
    val productDescription: String,
    val point: String,
    @SerializedName("delivery_info")
    val deliveryInfo: String,
    @SerializedName("delivery_fee")
    val deliveryFee: String,
    val prices: List<String>,
    @SerializedName("detail_section")
    val detailSection: List<String>
)