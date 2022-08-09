package com.woowahan.ordering.data.repository

import com.woowahan.ordering.data.datasource.OrderDataSource
import com.woowahan.ordering.data.mapper.toModel
import com.woowahan.ordering.domain.model.Cart
import com.woowahan.ordering.domain.model.Order
import com.woowahan.ordering.domain.model.SimpleOrder
import com.woowahan.ordering.domain.repository.OrderRepository
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val dataSource: OrderDataSource
): OrderRepository {
    override fun insertOrder(order: Order) {
        dataSource.insertOrder(order)
    }

    override fun getSimpleOrder(): List<SimpleOrder> {
        return dataSource.getSimpleOrder()
    }

    override fun getOrderedCartByDeliveryTime(deliveryTime: Long): List<Cart> {
        return dataSource.getOrderedCartByDeliveryTime(deliveryTime)
    }
}