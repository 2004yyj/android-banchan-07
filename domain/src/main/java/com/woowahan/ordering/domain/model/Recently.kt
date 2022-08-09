package com.woowahan.ordering.domain.model

data class Recently(
    val detailHash: String,
    val name: String,
    val thumbnail: String,
    val price: Long,
    val discountPrice: Long,
    val latestViewedTime: Long
)