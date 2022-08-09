package com.woowahan.ordering.di

import android.content.Context
import androidx.room.Room
import com.woowahan.ordering.data.local.dao.CartDao
import com.woowahan.ordering.data.local.dao.OrderDao
import com.woowahan.ordering.data.local.dao.RecentlyDao
import com.woowahan.ordering.data.local.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {
    @Provides
    @Singleton
    fun providesAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "ordering.db"
        ).build()
    }

    @Provides
    @Singleton
    fun providesCartDao(appDatabase: AppDatabase): CartDao {
        return appDatabase.cartDao()
    }

    @Provides
    @Singleton
    fun providesOrderDao(appDatabase: AppDatabase): OrderDao {
        return appDatabase.orderDao()
    }

    @Provides
    @Singleton
    fun providesRecentlyDao(appDatabase: AppDatabase): RecentlyDao {
        return appDatabase.recentlyDao()
    }
}