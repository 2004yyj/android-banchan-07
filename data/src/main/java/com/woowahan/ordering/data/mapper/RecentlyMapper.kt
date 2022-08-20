package com.woowahan.ordering.data.mapper

import com.woowahan.ordering.data.entity.RecentlyEntity
import com.woowahan.ordering.data.util.getDiscountRate
import com.woowahan.ordering.domain.model.Cart
import com.woowahan.ordering.domain.model.Recently

fun Recently.toEntity(): RecentlyEntity {
    return RecentlyEntity(
        detailHash,
        title,
        thumbnail,
        price,
        discountedPrice,
        latestViewedTime
    )
}

fun RecentlyEntity.toModel(): Recently {
    return Recently(
        detailHash,
        title,
        thumbnail,
        price,
        discountPrice,
        latestViewedTime
    )
}

fun Recently.toCartModel(): Cart {
    return Cart(
        title = title,
        thumbnail = thumbnail,
        discountedRate = discountedPrice.getDiscountRate(price),
        discountedPrice = discountedPrice,
        originalPrice = price,
        detailHash = detailHash
    )
}