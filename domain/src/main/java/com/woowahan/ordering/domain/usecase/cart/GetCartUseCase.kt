package com.woowahan.ordering.domain.usecase.cart

import com.woowahan.ordering.domain.model.Result
import com.woowahan.ordering.domain.repository.CartRepository
import kotlinx.coroutines.flow.flow

class GetCartUseCase(
    private val repository: CartRepository
) {
    operator fun invoke() = flow {
        emit(Result.Loading)
        repository.getCart().collect {
            emit(Result.Success(it))
        }
    }
}