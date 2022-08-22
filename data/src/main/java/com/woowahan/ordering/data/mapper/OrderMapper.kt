package com.woowahan.ordering.data.mapper

import com.woowahan.ordering.data.entity.OrderEntity
import com.woowahan.ordering.domain.model.Order

fun Order.toEntity(): OrderEntity {
    return OrderEntity(
        id,
        deliveryTime,
        isDelivered
    )
}

fun OrderEntity.toModel(): Order {
    return Order(
        id,
        deliveryTime,
        isDelivered
    )
}