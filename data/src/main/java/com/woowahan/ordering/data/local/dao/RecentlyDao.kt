package com.woowahan.ordering.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.woowahan.ordering.data.entity.RecentlyEntity
import com.woowahan.ordering.domain.model.Recently

@Dao
interface RecentlyDao {
    @Insert
    fun insertRecently(recently: RecentlyEntity)
    @Update
    fun updateRecently(recently: RecentlyEntity)
    @Query("SELECT * FROM Recently ORDER BY latestViewedTime DESC")
    fun getRecently(): List<RecentlyEntity>
    @Query("SELECT * FROM Recently ORDER BY latestViewedTime DESC LIMIT :size")
    fun getSimpleRecently(size: Int): List<RecentlyEntity>
}
