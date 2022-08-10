package com.woowahan.ordering.data.mapper

import com.woowahan.ordering.data.entity.FoodDetailResponse
import com.woowahan.ordering.data.util.toMoneyLong
import com.woowahan.ordering.domain.model.FoodDetail

fun FoodDetailResponse.toModel(): FoodDetail {
    val price = with(data) {
        if (data.prices.size > 1) prices[0].toMoneyLong() else 0
    }

    val discountPrice = with(data) {
        if (prices.size > 1) prices[1].toMoneyLong()
        else prices[0].toMoneyLong()
    }

    return FoodDetail(
        hash = hash,
        topImage = data.topImage,
        thumbImages = data.thumbImages,
        productDescription = data.productDescription,
        point = data.point.toMoneyLong(),
        deliveryInfo = data.deliveryInfo,
        deliveryFee = data.deliveryFee,
        price = price,
        discountPrice = discountPrice,
        detailSection = data.detailSection
    )
}