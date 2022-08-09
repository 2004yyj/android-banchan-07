package com.woowahan.ordering.domain.usecase.order

import com.woowahan.ordering.domain.model.Result
import com.woowahan.ordering.domain.repository.CartRepository
import com.woowahan.ordering.domain.repository.OrderRepository
import kotlinx.coroutines.flow.flow

class GetOrderedCartByDeliveryTimeUseCase(
    private val repository: OrderRepository
) {
    operator fun invoke(deliveryTime: Long) = flow {
        emit(Result.Loading)
        try {
            emit(Result.Success(repository.getOrderedCartByDeliveryTime(deliveryTime)))
        } catch(e: Exception) {
            emit(Result.Failure(e))
        }
    }
}