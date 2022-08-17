package com.woowahan.ordering.data.repository

import com.woowahan.ordering.data.datasource.CartDataSource
import com.woowahan.ordering.domain.model.Cart
import com.woowahan.ordering.domain.model.CartResult
import com.woowahan.ordering.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val dataSource: CartDataSource
) : CartRepository {
    override fun insertCart(cart: Cart) {
        dataSource.insertCart(cart)
    }

    override fun updateCart(cart: Cart) {
        dataSource.updateCart(cart)
    }

    override fun deleteCart(cart: Cart) {
        dataSource.deleteCart(cart)
    }

    override fun getCart(): Flow<List<Cart>> {
        return dataSource.getCart()
    }

    override fun isExistNotOrderedCart(detailHash: String): Boolean {
        return dataSource.isExistNotOrderedCart(detailHash)
    }

    override fun selectAllCartItem(option: Boolean) {
        dataSource.selectAllCartItem(option)
    }

    override fun updateAllSelectedItemsOrderId(orderId: Long) {
        dataSource.updateAllSelectedItemsOrderId(orderId)
    }

    override fun deleteAllSelectedItems() {
        dataSource.deleteAllSelectedItems()
    }

    override fun getCartResult(): Flow<CartResult> {
        return dataSource.getCartResult()
    }
}