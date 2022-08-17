package com.woowahan.ordering.domain.repository

import com.woowahan.ordering.domain.model.Cart
import com.woowahan.ordering.domain.model.Order
import com.woowahan.ordering.domain.model.OrderedCartList
import com.woowahan.ordering.domain.model.SimpleOrder
import kotlinx.coroutines.flow.Flow

interface OrderRepository {
    fun insertOrder(order: Order): Long
    fun getSimpleOrder(): Flow<List<SimpleOrder>>
    fun getOrderedCartByDeliveryTime(deliveryTime: Long): Flow<OrderedCartList>
}