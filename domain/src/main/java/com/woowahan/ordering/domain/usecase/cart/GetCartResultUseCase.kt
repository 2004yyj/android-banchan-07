package com.woowahan.ordering.domain.usecase.cart

import com.woowahan.ordering.domain.model.Result
import com.woowahan.ordering.domain.repository.CartRepository
import com.woowahan.ordering.domain.repository.HistoryRepository
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow

class GetCartResultUseCase(
    private val cartRepository: CartRepository,
    private val historyRepository: HistoryRepository
) {
    operator fun invoke() = flow {
        emit(Result.Loading)
        try {
            cartRepository.getCartResult()
                .combine(historyRepository.getSimpleHistories(7)) { cart, history ->
                    cart.copy(historyList = history)
                }.collect {
                    emit(Result.Success(it))
                }
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }
}