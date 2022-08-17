package com.woowahan.ordering.data.mapper

import com.woowahan.ordering.data.entity.FoodEntity
import com.woowahan.ordering.contracts.toMoneyLong
import com.woowahan.ordering.domain.model.Food

fun FoodEntity.toModel(): Food {

    val price = price?.toMoneyLong() ?: 0L
    val discountedPrice = discountedPrice.toMoneyLong()
    val discountedRate = if (price == 0L) 0 else (1 - (discountedPrice.toFloat() / price.toFloat())) * 100

    return Food(
        detailHash = detailHash,
        image = image,
        alt = alt,
        deliveryType = deliveryType,
        title = title,
        description = description,
        price = price,
        discountedPrice = discountedPrice,
        badge = badge,
        discountRate = discountedRate.toInt()
    )
}