package com.woowahan.ordering.data.mapper

import com.woowahan.ordering.data.entity.SimpleOrderEntity
import com.woowahan.ordering.domain.model.SimpleOrder

fun SimpleOrder.toEntity(): SimpleOrderEntity {
    return SimpleOrderEntity(
        name,
        deliveryTime,
        thumbnail,
        totalPrice,
        productCount
    )
}

fun SimpleOrderEntity.toModel(): SimpleOrder {
    return SimpleOrder(
        name,
        deliveryTime,
        thumbnail,
        totalPrice,
        productCount
    )
}