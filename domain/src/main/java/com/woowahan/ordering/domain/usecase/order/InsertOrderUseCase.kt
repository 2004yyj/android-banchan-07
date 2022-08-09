package com.woowahan.ordering.domain.usecase.order

import com.woowahan.ordering.domain.model.Order
import com.woowahan.ordering.domain.model.Result
import com.woowahan.ordering.domain.repository.OrderRepository
import kotlinx.coroutines.flow.flow

class InsertOrderUseCase(
    private val repository: OrderRepository
) {
    operator fun invoke(order: Order) = flow {
        emit(Result.Loading)
        try {
            emit(Result.Success(repository.insertOrder(order)))
        } catch(e: Exception) {
            emit(Result.Failure(e))
        }
    }
}