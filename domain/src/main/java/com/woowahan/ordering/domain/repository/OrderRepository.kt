package com.woowahan.ordering.domain.repository

import androidx.paging.PagingData
import com.woowahan.ordering.domain.model.Order
import com.woowahan.ordering.domain.model.OrderedCartList
import com.woowahan.ordering.domain.model.SimpleOrder
import kotlinx.coroutines.flow.Flow

interface OrderRepository {
    fun insertOrder(order: Order): Long
    fun getSimpleOrder(): Flow<PagingData<SimpleOrder>>
    fun getOrderedCartByDeliveryTime(deliveryTime: Long): OrderedCartList
    fun isExistNotDeliveredOrder(): Flow<Boolean>
    fun updateOrder(deliveryTime: Long, isDelivered: Boolean)
}