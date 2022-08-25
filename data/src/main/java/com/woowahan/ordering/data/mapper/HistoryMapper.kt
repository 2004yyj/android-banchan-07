package com.woowahan.ordering.data.mapper

import com.woowahan.ordering.data.entity.HistoryEntity
import com.woowahan.ordering.data.util.getDiscountRate
import com.woowahan.ordering.domain.model.Cart
import com.woowahan.ordering.domain.model.History

fun History.toEntity(): HistoryEntity {
    return HistoryEntity(
        detailHash,
        title,
        thumbnail,
        price,
        discountedPrice,
        latestViewedTime
    )
}

fun HistoryEntity.toModel(): History {
    return History(
        detailHash,
        title,
        thumbnail,
        price,
        discountPrice,
        latestViewedTime
    )
}

fun History.toCartModel(): Cart {
    return Cart(
        title = title,
        thumbnail = thumbnail,
        discountedRate = discountedPrice.getDiscountRate(price),
        discountedPrice = discountedPrice,
        originalPrice = price,
        detailHash = detailHash
    )
}