package com.woowahan.ordering.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.woowahan.ordering.data.entity.CartEntity
import com.woowahan.ordering.data.entity.OrderEntity
import com.woowahan.ordering.data.entity.RecentlyEntity
import com.woowahan.ordering.data.local.dao.CartDao

@Database(
    entities = [
        CartEntity::class,
        OrderEntity::class,
        RecentlyEntity::class
    ],
    version = 1
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun cartDao(): CartDao
}