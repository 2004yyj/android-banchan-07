package com.woowahan.ordering.data.datasource

import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.woowahan.ordering.domain.model.Order
import com.woowahan.ordering.domain.model.OrderedCartList
import com.woowahan.ordering.domain.model.SimpleOrder
import kotlinx.coroutines.flow.Flow

interface OrderDataSource {
    fun insertOrder(order: Order): Long
    fun getSimpleOrder(): Flow<PagingData<SimpleOrder>>
    fun getOrderedCartByDeliveryTime(deliveryTime: Long): OrderedCartList
    fun isExistNotDeliveredOrder(): Flow<Boolean>
    fun updateOrder(deliveryTime: Long, isDelivered: Boolean)
}