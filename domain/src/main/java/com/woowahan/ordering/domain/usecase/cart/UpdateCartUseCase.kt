package com.woowahan.ordering.domain.usecase.cart

import com.woowahan.ordering.domain.model.Cart
import com.woowahan.ordering.domain.model.Result
import com.woowahan.ordering.domain.repository.CartRepository
import kotlinx.coroutines.flow.flow

class UpdateCartUseCase(
    private val repository: CartRepository
) {
    operator fun invoke(cart: Cart) = flow {
        emit(Result.Loading)
        try {
            emit(Result.Success(repository.updateCart(cart)))
        } catch(e: Exception) {
            emit(Result.Failure(e))
        }
    }
}