package com.woowahan.ordering.domain.model

data class Best(
    val categoryId: String,
    val name: String,
    val items: List<Food>
)