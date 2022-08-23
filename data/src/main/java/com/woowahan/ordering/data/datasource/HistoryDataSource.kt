package com.woowahan.ordering.data.datasource

import com.woowahan.ordering.domain.model.History
import kotlinx.coroutines.flow.Flow

interface HistoryDataSource {
    fun insertHistory(history: History)
    fun updateHistory(history: History)
    fun getAllHistories(): List<History>
    fun getSimpleHistories(size: Int): Flow<List<History>>
}
