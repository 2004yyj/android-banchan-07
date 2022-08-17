package com.woowahan.ordering.domain.usecase.cart

import com.woowahan.ordering.domain.repository.CartRepository

class GetCartResultUseCase(
    private val repository: CartRepository
) {
    operator fun invoke() = repository.getCartResult()
}