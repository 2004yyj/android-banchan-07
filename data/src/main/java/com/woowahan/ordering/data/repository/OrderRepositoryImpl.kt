package com.woowahan.ordering.data.repository

import com.woowahan.ordering.data.datasource.OrderDataSource
import com.woowahan.ordering.domain.model.Cart
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

    override fun getSimpleOrder(): Flow<List<SimpleOrder>> {
        return dataSource.getSimpleOrder()
    }

    override fun getOrderedCartByDeliveryTime(deliveryTime: Long): Flow<OrderedCartList> {
        return dataSource.getOrderedCartByDeliveryTime(deliveryTime)
    }
}