package com.woowahan.ordering.data.mapper

import com.woowahan.ordering.data.entity.FoodEntity
import com.woowahan.ordering.data.util.toMoneyLong
import com.woowahan.ordering.domain.model.Food

fun FoodEntity.toModel(): Food {
    return Food(
        detailHash = detailHash,
        image = image,
        alt = alt,
        deliveryType = deliveryType,
        title = title,
        description = description,
        price = price?.toMoneyLong() ?: 0,
        discountedPrice = discountedPrice.toMoneyLong(),
        badge = badge
    )
}