package com.woowahan.ordering.di.usecase

import com.woowahan.ordering.domain.repository.CartRepository
import com.woowahan.ordering.domain.usecase.cart.DeleteCartUseCase
import com.woowahan.ordering.domain.usecase.cart.GetCartUseCase
import com.woowahan.ordering.domain.usecase.cart.InsertCartUseCase
import com.woowahan.ordering.domain.usecase.cart.UpdateCartUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CartUseCaseModule {
    @Provides
    @Singleton
    fun providesDeleteCartUseCase(repository: CartRepository): DeleteCartUseCase {
        return DeleteCartUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesGetCartUseCase(repository: CartRepository): GetCartUseCase {
        return GetCartUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesInsertCartUseCase(repository: CartRepository): InsertCartUseCase {
        return InsertCartUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesUpdateCartUseCase(repository: CartRepository): UpdateCartUseCase {
        return UpdateCartUseCase(repository)
    }
}