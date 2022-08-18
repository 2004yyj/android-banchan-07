package com.woowahan.ordering.data.datasource

import com.woowahan.ordering.domain.model.Order
import com.woowahan.ordering.domain.model.OrderedCartList
import com.woowahan.ordering.domain.model.SimpleOrder
import kotlinx.coroutines.flow.Flow

interface OrderDataSource {
    fun insertOrder(order: Order): Long
    fun getSimpleOrder(): Flow<List<SimpleOrder>>
    fun getOrderedCartByDeliveryTime(deliveryTime: Long): OrderedCartList
}