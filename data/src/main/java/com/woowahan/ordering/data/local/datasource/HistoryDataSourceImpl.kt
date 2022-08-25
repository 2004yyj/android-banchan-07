package com.woowahan.ordering.data.local.datasource

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.woowahan.ordering.constants.PAGING_DEFAULT_SIZE
import com.woowahan.ordering.data.datasource.HistoryDataSource
import com.woowahan.ordering.data.local.dao.HistoryDao
import com.woowahan.ordering.data.mapper.toEntity
import com.woowahan.ordering.data.mapper.toModel
import com.woowahan.ordering.domain.model.History
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HistoryDataSourceImpl @Inject constructor(
    private val historyDao: HistoryDao
) : HistoryDataSource {
    override fun insertHistory(history: History) {
        historyDao.insertHistory(history.toEntity())
    }

    override fun updateHistory(history: History) {
        historyDao.updateHistory(history.toEntity())
    }

    override fun getAllHistories(): Flow<PagingData<History>> {
        return Pager(PagingConfig(PAGING_DEFAULT_SIZE)) { historyDao.getAllHistories() }.flow.map {
            it.map { historyEntity -> historyEntity.toModel() }
        }
    }

    override fun getSimpleHistories(size: Int): Flow<List<History>> {
        return historyDao.getSimpleHistories(size).map {
            it.map { recently -> recently.toModel() }
        }
    }
}