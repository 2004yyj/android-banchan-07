package com.woowahan.ordering.domain.usecase.order

import com.woowahan.ordering.domain.model.Order
import com.woowahan.ordering.domain.model.Result
import com.woowahan.ordering.domain.repository.CartRepository
import com.woowahan.ordering.domain.repository.OrderRepository
import kotlinx.coroutines.flow.flow

class UpdateOrderUseCase(
    private val repository: OrderRepository
) {
    operator fun invoke(id: Long, isDelivered: Boolean) = repository.updateOrder(id, isDelivered)
}