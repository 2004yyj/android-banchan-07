package com.woowahan.ordering.data.local.dao

import androidx.room.*
import com.woowahan.ordering.data.entity.RecentlyEntity

@Dao
interface RecentlyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecently(recently: RecentlyEntity)

    @Update
    fun updateRecently(recently: RecentlyEntity)

    @Query("SELECT * FROM Recently ORDER BY latestViewedTime DESC")
    fun getRecently(): List<RecentlyEntity>

    @Query("SELECT * FROM Recently ORDER BY latestViewedTime DESC LIMIT :size")
    fun getSimpleRecently(size: Int): List<RecentlyEntity>
}
