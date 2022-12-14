package com.woowahan.ordering.domain.usecase.order

import com.woowahan.ordering.domain.model.Order
import com.woowahan.ordering.domain.model.Result
import com.woowahan.ordering.domain.repository.CartRepository
import com.woowahan.ordering.domain.repository.OrderRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class InsertOrderUseCase(
    private val orderRepository: OrderRepository,
    private val cartRepository: CartRepository,
    private val ioDispatcher: CoroutineDispatcher
) {
    operator fun invoke(order: Order) = flow {
        emit(Result.Loading)
        try {
            val orderId = orderRepository.insertOrder(order)
            emit(Result.Success(cartRepository.updateAllSelectedItemsOrderId(orderId)))
        } catch(e: Exception) {
            emit(Result.Failure(e))
        }
    }.flowOn(ioDispatcher)
}