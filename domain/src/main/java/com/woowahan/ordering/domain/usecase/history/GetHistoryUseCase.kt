package com.woowahan.ordering.domain.usecase.history

import androidx.paging.PagingData
import androidx.paging.map
import com.woowahan.ordering.domain.model.History
import com.woowahan.ordering.domain.repository.CartRepository
import com.woowahan.ordering.domain.repository.HistoryRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*

class GetHistoryUseCase(
    private val historyRepository: HistoryRepository,
    private val cartRepository: CartRepository,
    private val ioDispatcher: CoroutineDispatcher
) {
    operator fun invoke(): Flow<PagingData<History>> {
        return historyRepository.getAllHistories()
            .combine(cartRepository.getCart()) { history, cart ->
                val hashList = cart.map { it.detailHash }
                history.map {
                    it.copy(isAdded = hashList.contains(it.detailHash))
                }
            }.flowOn(ioDispatcher)
    }
}