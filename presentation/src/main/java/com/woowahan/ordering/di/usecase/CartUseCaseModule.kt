package com.woowahan.ordering.di.usecase

import com.woowahan.ordering.di.scope.IODispatcher
import com.woowahan.ordering.domain.repository.CartRepository
import com.woowahan.ordering.domain.repository.HistoryRepository
import com.woowahan.ordering.domain.usecase.cart.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CartUseCaseModule {
    @Provides
    @Singleton
    fun providesDeleteCartUseCase(
        repository: CartRepository,
        @IODispatcher ioDispatcher: CoroutineDispatcher,
    ): DeleteCartUseCase {
        return DeleteCartUseCase(repository, ioDispatcher)
    }

    @Provides
    @Singleton
    fun providesGetCartCountUseCase(
        repository: CartRepository,
        @IODispatcher ioDispatcher: CoroutineDispatcher,
    ): GetCartCountUseCase {
        return GetCartCountUseCase(repository, ioDispatcher)
    }

    @Provides
    @Singleton
    fun providesGetCartResultUseCase(
        cartRepository: CartRepository,
        historyRepository: HistoryRepository,
        @IODispatcher ioDispatcher: CoroutineDispatcher,
    ): GetCartResultUseCase {
        return GetCartResultUseCase(cartRepository, historyRepository, ioDispatcher)
    }

    @Provides
    @Singleton
    fun providesInsertCartUseCase(
        repository: CartRepository,
        @IODispatcher ioDispatcher: CoroutineDispatcher,
    ): InsertCartUseCase {
        return InsertCartUseCase(repository, ioDispatcher)
    }

    @Provides
    @Singleton
    fun providesUpdateCartUseCase(
        repository: CartRepository,
        @IODispatcher ioDispatcher: CoroutineDispatcher,
    ): UpdateCartUseCase {
        return UpdateCartUseCase(repository, ioDispatcher)
    }

    @Provides
    @Singleton
    fun providesSelectAllCartUseCase(
        repository: CartRepository,
        @IODispatcher ioDispatcher: CoroutineDispatcher,
    ): SelectAllCartUseCase {
        return SelectAllCartUseCase(repository, ioDispatcher)
    }

    @Provides
    @Singleton
    fun providesDeleteAllSelectedCartUseCase(
        repository: CartRepository,
        @IODispatcher ioDispatcher: CoroutineDispatcher,
    ): DeleteAllSelectedCartUseCase {
        return DeleteAllSelectedCartUseCase(repository, ioDispatcher)
    }
}