package com.woowahan.ordering.impl.dao

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.woowahan.ordering.data.entity.CartEntity
import com.woowahan.ordering.data.entity.OrderEntity
import com.woowahan.ordering.data.entity.SimpleOrderEntity
import com.woowahan.ordering.data.local.dao.OrderDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeOrderDao(
    private val orderedCartList: List<CartEntity>,
    private val simpleOrderList: List<SimpleOrderEntity>,
    private val orderList: ArrayList<OrderEntity>
) : OrderDao {
    override fun insertOrder(order: OrderEntity): Long {
        orderList.add(order)
        return orderList.size.toLong()
    }

    override fun updateOrder(deliveryTime: Long, isDelivered: Boolean) {}

    override fun getSimpleOrder(): PagingSource<Int, SimpleOrderEntity> {
        return object : PagingSource<Int, SimpleOrderEntity>() {
            override fun getRefreshKey(state: PagingState<Int, SimpleOrderEntity>): Int? {
                return state.anchorPosition
            }
            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SimpleOrderEntity> {
                return LoadResult.Page(simpleOrderList, null, 2)
            }
        }
    }

    override fun getOrderedCartByDeliveryTime(deliveryTime: Long): List<CartEntity> {
        val orderId = orderList.find { it.deliveryTime == deliveryTime }?.id
        return orderedCartList.filter { it.orderId == orderId }
    }

    override fun isExistNotDeliveredOrder(): Flow<Boolean> {
        return flow { !orderList.none { it.isDelivered } }
    }

}