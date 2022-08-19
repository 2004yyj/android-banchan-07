package com.woowahan.ordering.data.mapper

import com.woowahan.ordering.data.entity.FoodDetailResponse
import com.woowahan.ordering.constants.toMoneyLong
import com.woowahan.ordering.domain.model.FoodDetail

fun FoodDetailResponse.toModel(): FoodDetail {
    val price = with(data) {
        if (data.prices.size > 1) prices[0].toMoneyLong() else 0
    }

    val discountedPrice = with(data) {
        if (prices.size > 1) prices[1].toMoneyLong()
        else prices[0].toMoneyLong()
    }

    val discountedRate = if (price == 0L) 0 else (1 - (discountedPrice.toFloat() / price.toFloat())) * 100

    return FoodDetail(
        hash = hash,
        topImage = data.topImage,
        thumbImages = data.thumbImages,
        productDescription = data.productDescription,
        point = data.point.toMoneyLong(),
        deliveryInfo = data.deliveryInfo,
        deliveryFee = data.deliveryFee,
        price = price,
        discountedPrice = discountedPrice,
        discountedRate = discountedRate.toInt(),
        detailSection = data.detailSection
    )
}