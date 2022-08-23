package com.woowahan.ordering.domain.usecase.history

import com.woowahan.ordering.domain.model.Result
import com.woowahan.ordering.domain.repository.CartRepository
import com.woowahan.ordering.domain.repository.HistoryRepository
import kotlinx.coroutines.flow.flow

class GetHistoryUseCase(
    private val historyRepository: HistoryRepository,
    private val cartRepository: CartRepository
) {
    operator fun invoke() = flow {
        emit(Result.Loading)
        try {
            cartRepository.getCart().collect {
                val hashList = it.map { cart -> cart.detailHash }
                val historyList = historyRepository.getAllHistories()

                historyList.forEach { history ->
                    history.isAdded = hashList.contains(history.detailHash)
                }
                emit(Result.Success(historyList))
            }
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }
}