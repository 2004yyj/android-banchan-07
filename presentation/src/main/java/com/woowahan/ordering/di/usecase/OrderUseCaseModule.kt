package com.woowahan.ordering.di.usecase

import com.woowahan.ordering.domain.repository.CartRepository
import com.woowahan.ordering.domain.repository.OrderRepository
import com.woowahan.ordering.domain.usecase.cart.GetCartUseCase
import com.woowahan.ordering.domain.usecase.cart.InsertCartUseCase
import com.woowahan.ordering.domain.usecase.cart.UpdateCartUseCase
import com.woowahan.ordering.domain.usecase.order.GetOrderedCartByDeliveryTimeUseCase
import com.woowahan.ordering.domain.usecase.order.GetSimpleOrderUseCase
import com.woowahan.ordering.domain.usecase.order.InsertOrderUseCase
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
}