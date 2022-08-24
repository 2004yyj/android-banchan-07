package com.woowahan.ordering.domain.usecase.history

import com.woowahan.ordering.domain.repository.CartRepository
import com.woowahan.ordering.domain.repository.HistoryRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetHistoryUseCase(
    private val historyRepository: HistoryRepository,
    private val cartRepository: CartRepository,
    private val ioDispatcher: CoroutineDispatcher
) {
    operator fun invoke() = flow {
        cartRepository.getCart().collect {
            val hashList = it.map { cart -> cart.detailHash }
            val historyList = historyRepository.getAllHistories()

            historyList.forEach { history ->
                history.isAdded = hashList.contains(history.detailHash)
            }
            emit(historyList)
        }
    }.flowOn(ioDispatcher)
}