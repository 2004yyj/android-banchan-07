package com.woowahan.ordering.domain.usecase.history

import com.woowahan.ordering.domain.model.History
import com.woowahan.ordering.domain.model.Result
import com.woowahan.ordering.domain.repository.HistoryRepository
import kotlinx.coroutines.flow.flow

class InsertHistoryUseCase(
    private val repository: HistoryRepository
) {
    operator fun invoke(history: History) = flow {
        emit(Result.Loading)
        try {
            emit(Result.Success(repository.insertHistory(history)))
        } catch(e: Exception) {
            emit(Result.Failure(e))
        }
    }
}