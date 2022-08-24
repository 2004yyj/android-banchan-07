package com.woowahan.ordering.di.usecase

import com.woowahan.ordering.di.scope.IODispatcher
import com.woowahan.ordering.domain.repository.CartRepository
import com.woowahan.ordering.domain.repository.OrderRepository
import com.woowahan.ordering.domain.usecase.order.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OrderUseCaseModule {
    @Provides
    @Singleton
    fun providesGetOrderedCartByDeliveryTimeUseCase(
        repository: OrderRepository,
        @IODispatcher ioDispatcher: CoroutineDispatcher
    ): GetOrderedCartByDeliveryTimeUseCase {
        return GetOrderedCartByDeliveryTimeUseCase(repository, ioDispatcher)
    }

    @Provides
    @Singleton
    fun providesGetSimpleOrderUseCase(
        repository: OrderRepository,
        @IODispatcher ioDispatcher: CoroutineDispatcher
    ): GetSimpleOrderUseCase {
        return GetSimpleOrderUseCase(repository, ioDispatcher)
    }

    @Provides
    @Singleton
    fun providesInsertOrderUseCase(
        orderRepository: OrderRepository,
        cartRepository: CartRepository,
        @IODispatcher ioDispatcher: CoroutineDispatcher
    ): InsertOrderUseCase {
        return InsertOrderUseCase(orderRepository, cartRepository, ioDispatcher)
    }

    @Provides
    @Singleton
    fun providesUpdateOrderUseCase(
        repository: OrderRepository,
        @IODispatcher ioDispatcher: CoroutineDispatcher
    ): UpdateOrderUseCase {
        return UpdateOrderUseCase(repository, ioDispatcher)
    }

    @Provides
    @Singleton
    fun providesIsExistNotDeliveredOrderUseCase(
        repository: OrderRepository,
        @IODispatcher ioDispatcher: CoroutineDispatcher
    ): IsExistNotDeliveredOrderUseCase {
        return IsExistNotDeliveredOrderUseCase(repository, ioDispatcher)
    }
}