package com.woowahan.ordering.di

import com.woowahan.ordering.data.datasource.CartDataSource
import com.woowahan.ordering.data.datasource.OrderDataSource
import com.woowahan.ordering.data.datasource.RecentlyDataSource
import com.woowahan.ordering.data.local.datasource.CartDataSourceImpl
import com.woowahan.ordering.data.local.datasource.OrderDataSourceImpl
import com.woowahan.ordering.data.local.datasource.RecentlyDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {
    @Binds
    abstract fun bindsCartDataSource(cartDataSourceImpl: CartDataSourceImpl): CartDataSource

    @Binds
    abstract fun bindsOrderDataSource(orderDataSourceImpl: OrderDataSourceImpl): OrderDataSource

    @Binds
    abstract fun bindsRecentlyDataSource(recentlyDataSourceImpl: RecentlyDataSourceImpl): RecentlyDataSource
}