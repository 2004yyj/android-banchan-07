package com.woowahan.ordering.impl.dao

import com.woowahan.ordering.data.entity.CartEntity
import com.woowahan.ordering.data.local.dao.CartDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeCartDao(private val list: List<CartEntity>) : CartDao {
    override fun getCart(): Flow<List<CartEntity>> {
        return flow { emit(list) }
    }

    override fun isExistNotOrderedCart(detailHash: String): Boolean {
        return !list.none { it.detailHash == detailHash && it.orderId == null }
    }

    override fun insertCart(cart: CartEntity) {}
    override fun updateCart(cart: CartEntity) {}
    override fun deleteCart(cart: CartEntity) {}
    override fun selectAllCartItem(option: Boolean) {}
    override fun updateAllSelectedItemsOrderId(orderId: Long) {}
    override fun deleteAllSelectedItems() {}
}