package com.woowahan.ordering.data.mapper

import com.woowahan.ordering.data.entity.SimpleOrderEntity
import com.woowahan.ordering.domain.model.SimpleOrder

fun SimpleOrder.toEntity(): SimpleOrderEntity {
    return SimpleOrderEntity(
        title,
        deliveryTime,
        thumbnail,
        totalPrice,
        productCount
    )
}

fun SimpleOrderEntity.toModel(): SimpleOrder {
    return SimpleOrder(
        title,
        deliveryTime,
        thumbnail,
        totalPrice,
        productCount
    )
}