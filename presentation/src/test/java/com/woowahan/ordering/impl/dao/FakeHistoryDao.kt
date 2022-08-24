package com.woowahan.ordering.impl.dao

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.woowahan.ordering.constants.PAGING_DEFAULT_SIZE
import com.woowahan.ordering.data.entity.HistoryEntity
import com.woowahan.ordering.data.local.dao.HistoryDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeHistoryDao(private val list: List<HistoryEntity>) : HistoryDao {
    override fun getAllHistories(): PagingSource<Int, HistoryEntity> {
        return object : PagingSource<Int, HistoryEntity>() {
            override fun getRefreshKey(state: PagingState<Int, HistoryEntity>): Int? {
                return state.anchorPosition?.let { anchorPosition ->
                    val anchorPage = state.closestPageToPosition(anchorPosition)
                    anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
                }
            }
            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, HistoryEntity> {
                val page = params.key ?: 0
                return LoadResult.Page(
                    list,
                    prevKey = if (page == 0) null else page - 1,
                    nextKey = if (list.isEmpty()) null else page + 1
                )
            }
        }
    }

    override fun getSimpleHistories(size: Int): Flow<List<HistoryEntity>> {
        return flow { emit(list) }
    }

    override fun insertHistory(history: HistoryEntity) {}
    override fun updateHistory(history: HistoryEntity) {}
}