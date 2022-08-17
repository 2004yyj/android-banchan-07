package com.woowahan.ordering.data.mapper

import com.woowahan.ordering.data.entity.RecentlyEntity
import com.woowahan.ordering.domain.model.Cart
import com.woowahan.ordering.domain.model.Food
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
        id = 0,
        title = title,
        thumbnail = thumbnail,
        price = discountedPrice,
        count = 1,
        detailHash = detailHash
    )
}

fun Recently.toFoodModel(): Food {
    return Food(
        detailHash = detailHash,
        image = thumbnail,
        alt = "",
        deliveryType = listOf(),
        title = title,
        description = "",
        price = price,
        discountedPrice = discountedPrice,
        discountRate = (if (price == 0L) 0 else (1 - (discountedPrice.toFloat() / price.toFloat())) * 100).toInt(),
        badge = listOf(),
        isAdded = isAdded
    )
}