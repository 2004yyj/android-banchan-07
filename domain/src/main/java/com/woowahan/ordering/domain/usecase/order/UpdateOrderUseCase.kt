package com.woowahan.ordering.domain.usecase.order

import com.woowahan.ordering.domain.model.Result
import com.woowahan.ordering.domain.repository.OrderRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class UpdateOrderUseCase(
    private val repository: OrderRepository,
    private val ioDispatcher: CoroutineDispatcher
) {
    operator fun invoke(deliveryTime: Long, isDelivered: Boolean) = flow {
        emit(Result.Loading)
        try {
            emit(Result.Success(repository.updateOrder(deliveryTime, isDelivered)))
        } catch(e: Exception) {
            emit(Result.Failure(e))
        }
    }.flowOn(ioDispatcher)
}