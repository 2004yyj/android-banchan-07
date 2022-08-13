package com.woowahan.ordering.domain.model

data class Cart(
    val id: Int,
    val title: String,
    val thumbnail: String,
    val price: Long,
    val count: Int,
    val detailHash: String,
    val isChecked: Boolean = true
)