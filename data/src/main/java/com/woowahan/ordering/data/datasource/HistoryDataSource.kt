package com.woowahan.ordering.data.datasource

import androidx.paging.PagingData
import com.woowahan.ordering.domain.model.History
import kotlinx.coroutines.flow.Flow

interface HistoryDataSource {
    fun insertHistory(history: History)
    fun updateHistory(history: History)
    fun getAllHistories(): Flow<PagingData<History>>
    fun getSimpleHistories(size: Int): Flow<List<History>>
}
