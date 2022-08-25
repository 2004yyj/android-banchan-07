package com.woowahan.ordering.impl.dao

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.woowahan.ordering.data.entity.CartEntity
import com.woowahan.ordering.data.entity.HistoryEntity
import com.woowahan.ordering.data.entity.OrderEntity
import com.woowahan.ordering.data.entity.SimpleOrderEntity
import com.woowahan.ordering.data.local.dao.OrderDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeOrderDao(
    private val orderedCartList: List<CartEntity>,
    private val simpleOrderList: List<SimpleOrderEntity>,
    private val orderList: List<OrderEntity>
) : OrderDao {
    override fun insertOrder(order: OrderEntity): Long {
        return 0
    }

    override fun updateOrder(deliveryTime: Long, isDelivered: Boolean) {}

    override fun getSimpleOrder(): PagingSource<Int, SimpleOrderEntity> {
        return object : PagingSource<Int, SimpleOrderEntity>() {
            override fun getRefreshKey(state: PagingState<Int, SimpleOrderEntity>): Int? {
                return state.anchorPosition?.let { anchorPosition ->
                    val anchorPage = state.closestPageToPosition(anchorPosition)
                    anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
                }
            }
            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SimpleOrderEntity> {
                val page = params.key ?: 0
                return LoadResult.Page(
                    simpleOrderList,
                    prevKey = if (page == 0) null else page - 1,
                    nextKey = if (simpleOrderList.isEmpty()) null else page + 1
                )
            }
        }
    }

    override fun getOrderedCartByDeliveryTime(deliveryTime: Long): List<CartEntity> {
        val orderId = orderList.find { it.deliveryTime == deliveryTime }?.id
        return orderedCartList.filter { it.orderId == orderId }
    }

    override fun isExistNotDeliveredOrder(): Flow<Boolean> {
        return flow { emit(!orderList.none { it.isDelivered }) }
    }

}