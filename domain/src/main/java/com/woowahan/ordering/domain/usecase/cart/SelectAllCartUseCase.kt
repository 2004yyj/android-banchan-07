package com.woowahan.ordering.domain.usecase.cart

import com.woowahan.ordering.domain.model.Result
import com.woowahan.ordering.domain.repository.CartRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class SelectAllCartUseCase(
    private val repository: CartRepository,
    private val ioDispatcher: CoroutineDispatcher
) {
    operator fun invoke(option: Boolean) = flow {
        emit(Result.Loading)
        try {
            emit(Result.Success(repository.selectAllCartItem(option)))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }.flowOn(ioDispatcher)
}
