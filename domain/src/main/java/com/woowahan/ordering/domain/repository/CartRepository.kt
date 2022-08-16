package com.woowahan.ordering.domain.repository

import com.woowahan.ordering.domain.model.Cart

interface CartRepository {
    fun insertCart(cart: Cart)
    fun updateCart(cart: Cart)
    fun deleteCart(cart: Cart)
    fun getCart(): List<Cart>
    fun isExistNotOrderedCart(detailHash: String): Boolean
}