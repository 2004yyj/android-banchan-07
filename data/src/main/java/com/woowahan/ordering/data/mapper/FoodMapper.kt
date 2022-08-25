package com.woowahan.ordering.data.mapper

import com.woowahan.ordering.data.entity.FoodEntity
import com.woowahan.ordering.data.util.getDiscountRate
import com.woowahan.ordering.data.util.toMoneyLong
import com.woowahan.ordering.domain.model.Cart
import com.woowahan.ordering.domain.model.Food

fun FoodEntity.toModel(): Food {

    val price = price?.toMoneyLong() ?: 0L
    val discountedPrice = discountedPrice.toMoneyLong()
    val discountedRate = discountedPrice.getDiscountRate(price)

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
        discountRate = discountedRate
    )
}

fun Food.toCartModel(): Cart {
    return Cart(
        title = title,
        thumbnail = image,
        discountedRate = discountRate,
        originalPrice = price,
        discountedPrice = discountedPrice,
        detailHash = detailHash
    )
}