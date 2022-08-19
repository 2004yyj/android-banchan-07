package com.woowahan.ordering.di.usecase

import com.woowahan.ordering.domain.repository.CartRepository
import com.woowahan.ordering.domain.repository.OrderRepository
import com.woowahan.ordering.domain.usecase.order.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OrderUseCaseModule {
    @Provides
    @Singleton
    fun providesGetOrderedCartByDeliveryTimeUseCase(repository: OrderRepository): GetOrderedCartByDeliveryTimeUseCase {
        return GetOrderedCartByDeliveryTimeUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesGetSimpleOrderUseCase(repository: OrderRepository): GetSimpleOrderUseCase {
        return GetSimpleOrderUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesInsertOrderUseCase(orderRepository: OrderRepository, cartRepository: CartRepository): InsertOrderUseCase {
        return InsertOrderUseCase(orderRepository, cartRepository)
    }

    @Provides
    @Singleton
    fun providesUpdateOrderUseCase(repository: OrderRepository): UpdateOrderUseCase {
        return UpdateOrderUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesIsExistNotDeliveredOrderUseCase(repository: OrderRepository): IsExistNotDeliveredOrderUseCase {
        return IsExistNotDeliveredOrderUseCase(repository)
    }
}