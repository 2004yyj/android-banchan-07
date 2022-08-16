package com.woowahan.ordering.data.local.datasource

import com.woowahan.ordering.data.datasource.CartDataSource
import com.woowahan.ordering.data.local.dao.CartDao
import com.woowahan.ordering.data.mapper.toEntity
import com.woowahan.ordering.data.mapper.toModel
import com.woowahan.ordering.domain.model.Cart
import javax.inject.Inject

class CartDataSourceImpl @Inject constructor(
    private val cartDao: CartDao
): CartDataSource {
    override fun insertCart(cart: Cart) {
        cartDao.insertCart(cart.toEntity())
    }

    override fun updateCart(cart: Cart) {
        cartDao.updateCart(cart.toEntity())
    }

    override fun deleteCart(cart: Cart) {
        cartDao.deleteCart(cart.toEntity())
    }

    override fun getCart(): List<Cart> {
        return cartDao.getCart().map {
            it.toModel()
        }
    }

    override fun isExistNotOrderedCart(detailHash: String): Boolean {
        return cartDao.isExistNotOrderedCart(detailHash)
    }
}