package com.woowahan.ordering.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.woowahan.ordering.data.entity.CartEntity
import com.woowahan.ordering.data.entity.OrderEntity
import com.woowahan.ordering.data.entity.HistoryEntity
import com.woowahan.ordering.data.local.dao.CartDao
import com.woowahan.ordering.data.local.dao.OrderDao
import com.woowahan.ordering.data.local.dao.HistoryDao

@Database(
    entities = [
        CartEntity::class,
        OrderEntity::class,
        HistoryEntity::class
    ],
    version = 1
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun cartDao(): CartDao
    abstract fun orderDao(): OrderDao
    abstract fun historyDao(): HistoryDao
}