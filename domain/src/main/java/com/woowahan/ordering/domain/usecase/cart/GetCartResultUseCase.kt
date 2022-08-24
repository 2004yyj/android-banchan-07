package com.woowahan.ordering.domain.usecase.cart

import com.woowahan.ordering.domain.repository.CartRepository
import com.woowahan.ordering.domain.repository.HistoryRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetCartResultUseCase(
    private val cartRepository: CartRepository,
    private val historyRepository: HistoryRepository,
    private val ioDispatcher: CoroutineDispatcher
) {
    operator fun invoke() = flow {
        cartRepository.getCartResult()
            .combine(historyRepository.getSimpleHistories(7)) { cart, history ->
                cart.copy(historyList = history)
            }.collect {
                emit(it)
            }
    }.flowOn(ioDispatcher)
}