package com.woowahan.ordering.domain.usecase.cart

import com.woowahan.ordering.domain.repository.CartRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetCartCountUseCase(
    private val repository: CartRepository,
    private val ioDispatcher: CoroutineDispatcher
) {
    operator fun invoke() = flow {
        repository.getCart().collect {
            emit(it.size)
        }
    }.flowOn(ioDispatcher)
}