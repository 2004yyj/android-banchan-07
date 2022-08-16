package com.woowahan.ordering.domain.repository

import com.woowahan.ordering.domain.model.Cart
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    fun insertCart(cart: Cart)
    fun updateCart(cart: Cart)
    fun deleteCart(cart: Cart)
    fun getCart(): Flow<List<Cart>>
    fun isExistNotOrderedCart(detailHash: String): Boolean
    fun selectAllCartItem(option: Boolean)
    fun updateAllSelectedItemsOrderId(orderId: Long)
}