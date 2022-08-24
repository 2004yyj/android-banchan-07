package com.woowahan.ordering.domain.usecase.order

import com.woowahan.ordering.domain.repository.OrderRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetOrderedCartByDeliveryTimeUseCase(
    private val repository: OrderRepository,
    private val ioDispatcher: CoroutineDispatcher
) {
    operator fun invoke(deliveryTime: Long) = flow {
        emit(repository.getOrderedCartByDeliveryTime(deliveryTime))
    }.flowOn(ioDispatcher)
}