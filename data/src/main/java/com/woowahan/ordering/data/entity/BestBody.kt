package com.woowahan.ordering.data.entity

import com.google.gson.annotations.SerializedName

data class BestBody(
    @SerializedName("category_id")
    val categoryId: String,
    val name: String,
    val items: List<FoodEntity>
)