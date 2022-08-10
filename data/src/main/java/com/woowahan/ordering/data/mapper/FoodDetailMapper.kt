package com.woowahan.ordering.data.mapper

import com.woowahan.ordering.data.entity.FoodDetailEntity
import com.woowahan.ordering.data.util.toMoneyLong
import com.woowahan.ordering.domain.model.FoodDetail

fun FoodDetailEntity.toModel(): FoodDetail {
    val price = if (prices.size > 1) prices[0].toMoneyLong() else 0
    val discountPrice =
        if (prices.size > 1) prices[1].toMoneyLong()
        else prices[0].toMoneyLong()

    return FoodDetail(
        topImage,
        thumbImages,
        productDescription,
        point.toMoneyLong(),
        deliveryInfo,
        deliveryFee,
        price,
        discountPrice,
        detailSection
    )
}