package com.woowahan.ordering.domain.usecase.order

import com.woowahan.ordering.domain.repository.OrderRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class IsExistNotDeliveredOrderUseCase constructor(
    private val repository: OrderRepository
) {
    operator fun invoke() = flow {
        repository.isExistNotDeliveredOrder().collect {
            emit(it)
        }
    }.flowOn(Dispatchers.IO)
}