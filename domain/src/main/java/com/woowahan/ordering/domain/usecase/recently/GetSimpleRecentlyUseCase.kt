package com.woowahan.ordering.domain.usecase.recently

import com.woowahan.ordering.domain.model.Result
import com.woowahan.ordering.domain.repository.RecentlyRepository
import kotlinx.coroutines.flow.flow

class GetSimpleRecentlyUseCase(
    private val repository: RecentlyRepository
) {
    operator fun invoke(size: Int = 7) = flow {
        emit(Result.Loading)
        try {
            emit(Result.Success(repository.getSimpleRecently(size)))
        } catch(e: Exception) {
            emit(Result.Failure(e))
        }
    }
}