package com.woowahan.ordering.data.mapper

import com.woowahan.ordering.contracts.DEFAULT_DELIVERY_FEE
import com.woowahan.ordering.contracts.DELIVERY_FREE_LIMIT
import com.woowahan.ordering.contracts.ORDER_MINIMUM_AMOUNT
import com.woowahan.ordering.data.entity.CartEntity
import com.woowahan.ordering.domain.model.Cart
import com.woowahan.ordering.domain.model.CartResult

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

fun List<CartEntity>.toCartResult(): CartResult{
    val list = map { cart -> cart.toModel() }
    val checkedList = list.filter { it.isChecked }
    val sum = checkedList.sumOf { it.price * it.count }

    return CartResult(
        isSelectedAll = checkedList.size == this.size,
        list = list,
        sum = checkedList.sumOf { it.price * it.count },
        deliveryFee = if (sum >= DELIVERY_FREE_LIMIT) 0 else DEFAULT_DELIVERY_FEE,
        insufficientAmount = (if (sum >= DELIVERY_FREE_LIMIT) 0 else DELIVERY_FREE_LIMIT - sum).toInt(),
        enableToOrder = sum >= ORDER_MINIMUM_AMOUNT
    )
}