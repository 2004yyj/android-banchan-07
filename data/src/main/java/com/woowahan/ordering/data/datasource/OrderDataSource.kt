package com.woowahan.ordering.data.datasource

import com.woowahan.ordering.domain.model.Cart
import com.woowahan.ordering.domain.model.Order
import com.woowahan.ordering.domain.model.SimpleOrder

interface OrderDataSource {
    fun insertOrder(order: Order)
    fun getSimpleOrder(): List<SimpleOrder>
    fun getOrderedCartByDeliveryTime(deliveryTime: Long): List<Cart>
}