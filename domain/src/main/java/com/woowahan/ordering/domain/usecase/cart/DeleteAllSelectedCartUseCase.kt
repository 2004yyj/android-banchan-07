package com.woowahan.ordering.domain.usecase.cart

import com.woowahan.ordering.domain.model.Result
import com.woowahan.ordering.domain.repository.CartRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class DeleteAllSelectedCartUseCase(
    private val repository: CartRepository,
    private val ioDispatcher: CoroutineDispatcher
) {
    operator fun invoke() = flow {
        emit(Result.Loading)
        try {
            emit(Result.Success(repository.deleteAllSelectedItems()))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }.flowOn(ioDispatcher)
}