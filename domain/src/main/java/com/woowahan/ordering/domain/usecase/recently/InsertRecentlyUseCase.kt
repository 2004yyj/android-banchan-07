package com.woowahan.ordering.domain.usecase.recently

import com.woowahan.ordering.domain.model.Recently
import com.woowahan.ordering.domain.model.Result
import com.woowahan.ordering.domain.repository.RecentlyRepository
import kotlinx.coroutines.flow.flow

class InsertRecentlyUseCase(
    private val repository: RecentlyRepository
) {
    operator fun invoke(recently: Recently) = flow {
        emit(Result.Loading)
        try {
            emit(Result.Success(repository.insertRecently(recently)))
        } catch(e: Exception) {
            emit(Result.Failure(e))
        }
    }
}