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
    fun providesDeleteCartUseCase(repository: CartRepository): DeleteCartUseCase {
        return DeleteCartUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesGetCartCountUseCase(repository: CartRepository): GetCartCountUseCase {
        return GetCartCountUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesGetCartResultUseCase(
        cartRepository: CartRepository,
        historyRepository: HistoryRepository
    ): GetCartResultUseCase {
        return GetCartResultUseCase(cartRepository, historyRepository)
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

    @Provides
    @Singleton
    fun providesSelectAllCartUseCase(repository: CartRepository): SelectAllCartUseCase {
        return SelectAllCartUseCase(repository)
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