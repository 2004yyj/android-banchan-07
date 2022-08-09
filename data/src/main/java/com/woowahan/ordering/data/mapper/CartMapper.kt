package com.woowahan.ordering.data.mapper

import com.woowahan.ordering.data.entity.CartEntity
import com.woowahan.ordering.domain.model.Cart

fun CartEntity.toModel(): Cart {
    return Cart(
        id = id,
        title = title,
        thumbnail = thumbnail,
        price = price,
        count = count,
        detailHash = detailHash,
        isChecked = isChecked
    )
}

fun Cart.toEntity(): CartEntity {
    return CartEntity(
        id = id,
        title = title,
        thumbnail = thumbnail,
        price = price,
        count = count,
        detailHash = detailHash,
        isChecked = isChecked,
        orderId = null
    )
}