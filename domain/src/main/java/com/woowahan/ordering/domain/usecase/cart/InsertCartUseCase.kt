package com.woowahan.ordering.domain.usecase.cart

import com.woowahan.ordering.domain.model.Cart
import com.woowahan.ordering.domain.model.Result
import com.woowahan.ordering.domain.repository.CartRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class InsertCartUseCase(
    private val repository: CartRepository,
    private val ioDispatcher: CoroutineDispatcher
) {
    operator fun invoke(cart: Cart) = flow {
        emit(Result.Loading)
        try {
            if (!repository.isExistNotOrderedCart(cart.detailHash)) {
                emit(Result.Success(repository.insertCart(cart)))
            } else {
                throw ExistOrderedCartException()
            }
        } catch(e: Exception) {
            emit(Result.Failure(e))
        }
    }.flowOn(ioDispatcher)
}

class ExistOrderedCartException: Exception()