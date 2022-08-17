package com.woowahan.ordering.domain.usecase.order

import com.woowahan.ordering.domain.repository.OrderRepository
import kotlinx.coroutines.flow.flow

class GetSimpleOrderUseCase(
    private val repository: OrderRepository
) {
    operator fun invoke() = flow {
        repository.getSimpleOrder().collect {
            emit(it)
        }
    }
}