package com.woowahan.ordering.data.repository

import androidx.paging.PagingData
import com.woowahan.ordering.data.datasource.HistoryDataSource
import com.woowahan.ordering.domain.model.History
import com.woowahan.ordering.domain.repository.HistoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HistoryRepositoryImpl @Inject constructor(
    private val dataSource: HistoryDataSource
) : HistoryRepository {
    override fun insertHistory(history: History) {
        return dataSource.insertHistory(history)
    }

    override fun updateHistory(history: History) {
        return dataSource.updateHistory(history)
    }

    override fun getAllHistories(): Flow<PagingData<History>> {
        return dataSource.getAllHistories()
    }

    override fun getSimpleHistories(size: Int): Flow<List<History>> {
        return dataSource.getSimpleHistories(size)
    }
}