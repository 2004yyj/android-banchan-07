package com.woowahan.ordering.domain.usecase.order

import com.woowahan.ordering.domain.repository.OrderRepository
import kotlinx.coroutines.flow.flow

class IsExistNotDeliveredOrderUseCase constructor(
    private val repository: OrderRepository
) {
    operator fun invoke() = flow {
        repository.isExistNotDeliveredOrder().collect {
            emit(it)
        }
    }
}