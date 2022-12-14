package com.woowahan.ordering.data.mapper

import com.woowahan.ordering.constants.DELIVERY_FREE_LIMIT
import com.woowahan.ordering.data.entity.CartEntity
import com.woowahan.ordering.domain.model.OrderedCartList

fun List<CartEntity>.toOrderList(): OrderedCartList {
    val count = size
    val totalPrice = sumOf { it.price * it.count }
    val list = map { cart -> cart.toModel() }
    return OrderedCartList(
        count = count,
        sumOfPrice = totalPrice,
        isNeedDeliveryFee = totalPrice < DELIVERY_FREE_LIMIT,
        list = list
    )
}