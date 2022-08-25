package com.woowahan.ordering.data.repository

import androidx.paging.PagingData
import com.woowahan.ordering.data.datasource.OrderDataSource
import com.woowahan.ordering.domain.model.Order
import com.woowahan.ordering.domain.model.OrderedCartList
import com.woowahan.ordering.domain.model.SimpleOrder
import com.woowahan.ordering.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val dataSource: OrderDataSource
) : OrderRepository {
    override fun insertOrder(order: Order): Long {
        return dataSource.insertOrder(order)
    }

    override fun updateOrder(deliveryTime: Long, isDelivered: Boolean) {
        return dataSource.updateOrder(deliveryTime, isDelivered)
    }

    override fun getSimpleOrder(): Flow<PagingData<SimpleOrder>> {
        return dataSource.getSimpleOrder()
    }

    override fun getOrderedCartByDeliveryTime(deliveryTime: Long): OrderedCartList {
        return dataSource.getOrderedCartByDeliveryTime(deliveryTime)
    }

    override fun isExistNotDeliveredOrder(): Flow<Boolean> {
        return dataSource.isExistNotDeliveredOrder()
    }
}