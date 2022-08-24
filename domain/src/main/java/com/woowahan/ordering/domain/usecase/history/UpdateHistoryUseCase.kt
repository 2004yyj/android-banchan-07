package com.woowahan.ordering.domain.usecase.history

import com.woowahan.ordering.domain.model.History
import com.woowahan.ordering.domain.model.Result
import com.woowahan.ordering.domain.repository.HistoryRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class UpdateHistoryUseCase(
    private val repository: HistoryRepository,
    private val ioDispatcher: CoroutineDispatcher
) {
    operator fun invoke(history: History) = flow {
        emit(Result.Loading)
        try {
            emit(Result.Success(repository.updateHistory(history)))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }.flowOn(ioDispatcher)
}