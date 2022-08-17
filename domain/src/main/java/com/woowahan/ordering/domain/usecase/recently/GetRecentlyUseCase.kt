package com.woowahan.ordering.domain.usecase.recently

import com.woowahan.ordering.domain.model.Result
import com.woowahan.ordering.domain.repository.CartRepository
import com.woowahan.ordering.domain.repository.RecentlyRepository
import kotlinx.coroutines.flow.flow

class GetRecentlyUseCase(
    private val recentlyRepository: RecentlyRepository,
    private val cartRepository: CartRepository
) {
    operator fun invoke() = flow {
        emit(Result.Loading)
        try {
            cartRepository.getCart().collect {
                val hashList = it.map { cart -> cart.detailHash }
                val recentlyList = recentlyRepository.getRecently()

                recentlyList.forEach { recently ->
                    recently.isAdded = hashList.contains(recently.detailHash)
                }
                emit(Result.Success(recentlyList))
            }
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }
}