package com.woowahan.ordering.domain.repository

import com.woowahan.ordering.domain.model.Cart
import com.woowahan.ordering.domain.model.Order
import com.woowahan.ordering.domain.model.SimpleOrder

interface OrderRepository {
    fun insertOrder(order: Order): Long
    fun getSimpleOrder(): List<SimpleOrder>
    fun getOrderedCartByDeliveryTime(deliveryTime: Long): List<Cart>
}