package com.woowahan.ordering.domain.usecase.order

import com.woowahan.ordering.domain.repository.OrderRepository
import kotlinx.coroutines.flow.flow

class GetOrderedCartByDeliveryTimeUseCase(
    private val repository: OrderRepository
) {
    operator fun invoke(deliveryTime: Long) = flow {
        emit(repository.getOrderedCartByDeliveryTime(deliveryTime))
    }
}