package com.woowahan.ordering.domain.usecase.order

import com.woowahan.ordering.domain.repository.OrderRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class IsExistNotDeliveredOrderUseCase constructor(
    private val repository: OrderRepository,
    private val ioDispatcher: CoroutineDispatcher
) {
    operator fun invoke() = flow {
        repository.isExistNotDeliveredOrder().collect {
            emit(it)
        }
    }.flowOn(ioDispatcher)
}