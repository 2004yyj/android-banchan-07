package com.woowahan.ordering.data.local.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.woowahan.ordering.data.entity.HistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHistory(history: HistoryEntity)

    @Update
    fun updateHistory(history: HistoryEntity)

    @Query("SELECT * FROM History ORDER BY latestViewedTime DESC")
    fun getAllHistories(): PagingSource<Int, HistoryEntity>

    @Query("SELECT * FROM History ORDER BY latestViewedTime DESC LIMIT :size")
    fun getSimpleHistories(size: Int): Flow<List<HistoryEntity>>
}
