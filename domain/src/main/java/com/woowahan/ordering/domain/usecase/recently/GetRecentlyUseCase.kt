package com.woowahan.ordering.domain.usecase.recently

import com.woowahan.ordering.domain.model.Result
import com.woowahan.ordering.domain.repository.RecentlyRepository
import kotlinx.coroutines.flow.flow

class GetRecentlyUseCase(
    private val repository: RecentlyRepository
) {
    operator fun invoke() = flow {
        emit(Result.Loading)
        try {
            emit(Result.Success(repository.getRecently()))
        } catch(e: Exception) {
            emit(Result.Failure(e))
        }
    }
}