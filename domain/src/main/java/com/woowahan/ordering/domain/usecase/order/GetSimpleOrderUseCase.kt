package com.woowahan.ordering.domain.usecase.order

import com.woowahan.ordering.domain.model.Result
import com.woowahan.ordering.domain.repository.OrderRepository
import kotlinx.coroutines.flow.flow

class GetSimpleOrderUseCase(
    private val repository: OrderRepository
) {
    operator fun invoke() = flow {
        emit(Result.Loading)
        try {
            emit(Result.Success(repository.getSimpleOrder()))
        } catch(e: Exception) {
            emit(Result.Failure(e))
        }
    }
}