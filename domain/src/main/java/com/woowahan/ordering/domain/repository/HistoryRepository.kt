package com.woowahan.ordering.domain.repository

import androidx.paging.PagingData
import com.woowahan.ordering.domain.model.History
import kotlinx.coroutines.flow.Flow

interface HistoryRepository {
    fun insertHistory(history: History)
    fun updateHistory(history: History)
    fun getAllHistories(): Flow<PagingData<History>>
    fun getSimpleHistories(size: Int): Flow<List<History>>
}