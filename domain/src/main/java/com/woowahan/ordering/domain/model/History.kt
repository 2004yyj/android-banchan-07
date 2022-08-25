package com.woowahan.ordering.domain.model

data class History(
    val detailHash: String,
    val title: String,
    val thumbnail: String,
    val price: Long,
    val discountedPrice: Long,
    val latestViewedTime: Long,
    var isAdded: Boolean = false
)