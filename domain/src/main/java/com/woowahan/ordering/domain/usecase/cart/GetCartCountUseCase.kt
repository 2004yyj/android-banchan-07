package com.woowahan.ordering.domain.usecase.cart

import com.woowahan.ordering.domain.repository.CartRepository
import kotlinx.coroutines.flow.flow

class GetCartCountUseCase(
    private val repository: CartRepository
) {
    operator fun invoke() = flow {
        repository.getCart().collect {
            emit(it.size)
        }
    }
}