package com.woowahan.ordering.domain.usecase.cart

import com.woowahan.ordering.domain.model.Cart
import com.woowahan.ordering.domain.model.Result
import com.woowahan.ordering.domain.repository.CartRepository
import kotlinx.coroutines.flow.flow

class InsertCartUseCase(
    private val repository: CartRepository
) {
    operator fun invoke(cart: Cart) = flow {
        emit(Result.Loading)
        try {
            repository.getCart().find {
                it.title == cart.title
            }.let {
                if (it != null) {
                    val cart = cart.copy(id = it.id)
                    emit(Result.Success(repository.updateCart(cart)))
                } else {
                    emit(Result.Success(repository.insertCart(cart)))
                }
            }
        } catch(e: Exception) {
            emit(Result.Failure(e))
        }
    }
}