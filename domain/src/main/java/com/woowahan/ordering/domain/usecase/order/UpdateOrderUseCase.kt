package com.woowahan.ordering.domain.usecase.order

import com.woowahan.ordering.domain.repository.OrderRepository

class UpdateOrderUseCase(
    private val repository: OrderRepository
) {
    operator fun invoke(id: Long, isDelivered: Boolean) = repository.updateOrder(id, isDelivered)
}