package com.woowahan.ordering.domain.usecase.order

import com.woowahan.ordering.domain.repository.OrderRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetSimpleOrderUseCase(
    private val repository: OrderRepository,
    private val ioDispatcher: CoroutineDispatcher
) {
    operator fun invoke() =
        repository.getSimpleOrder()
            .flowOn(ioDispatcher)
}