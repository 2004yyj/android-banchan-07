package com.woowahan.ordering.data.local.datasource

import com.woowahan.ordering.data.datasource.OrderDataSource
import com.woowahan.ordering.data.local.dao.OrderDao
import com.woowahan.ordering.data.mapper.toEntity
import com.woowahan.ordering.data.mapper.toModel
import com.woowahan.ordering.domain.model.Cart
import com.woowahan.ordering.domain.model.Order
import com.woowahan.ordering.domain.model.SimpleOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OrderDataSourceImpl @Inject constructor(
    private val orderDao: OrderDao
) : OrderDataSource {
    override fun insertOrder(order: Order): Long {
        return orderDao.insertOrder(order.toEntity())
    }

    override fun getSimpleOrder(): Flow<List<SimpleOrder>> {
        return orderDao.getSimpleOrder().map {
            it.map { simpleOrder -> simpleOrder.toModel() }
        }
    }

    override fun getOrderedCartByDeliveryTime(deliveryTime: Long): Flow<List<Cart>> {
        return orderDao.getOrderedCartByDeliveryTime(deliveryTime).map {
            it.map { cart -> cart.toModel() }
        }
    }

}