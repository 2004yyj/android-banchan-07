package com.woowahan.ordering.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.util.*

@Entity(tableName = "History")
data class HistoryEntity(
    @PrimaryKey(autoGenerate = false)
    val detailHash: String,
    val title: String,
    val thumbnail: String,
    val price: Long,
    val discountPrice: Long,
    val latestViewedTime: Long
)