package com.woowahan.ordering.data.datasource

import com.woowahan.ordering.data.entity.CartEntity
import com.woowahan.ordering.domain.model.Cart

interface CartDataSource {
    fun insertCart(cart: Cart)
    fun updateCart(cart: Cart)
    fun deleteCart(cart: Cart)
    fun getCart(): List<Cart>
}