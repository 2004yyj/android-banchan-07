package com.woowahan.ordering.di

import com.woowahan.ordering.data.repository.CartRepositoryImpl
import com.woowahan.ordering.data.repository.FoodRepositoryImpl
import com.woowahan.ordering.data.repository.OrderRepositoryImpl
import com.woowahan.ordering.data.repository.HistoryRepositoryImpl
import com.woowahan.ordering.domain.repository.CartRepository
import com.woowahan.ordering.domain.repository.FoodRepository
import com.woowahan.ordering.domain.repository.OrderRepository
import com.woowahan.ordering.domain.repository.HistoryRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindsCartRepository(cartRepository: CartRepositoryImpl): CartRepository

    @Binds
    abstract fun bindsOrderRepository(orderRepositoryImpl: OrderRepositoryImpl): OrderRepository

    @Binds
    abstract fun bindsRecentlyRepository(historyRepositoryImpl: HistoryRepositoryImpl): HistoryRepository

    @Binds
    abstract fun bindsFoodRepository(foodRepositoryImpl: FoodRepositoryImpl): FoodRepository
}