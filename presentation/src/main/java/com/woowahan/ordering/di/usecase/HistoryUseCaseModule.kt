package com.woowahan.ordering.di.usecase

import com.woowahan.ordering.di.scope.IODispatcher
import com.woowahan.ordering.domain.repository.CartRepository
import com.woowahan.ordering.domain.repository.HistoryRepository
import com.woowahan.ordering.domain.usecase.history.GetHistoryUseCase
import com.woowahan.ordering.domain.usecase.history.InsertHistoryUseCase
import com.woowahan.ordering.domain.usecase.history.UpdateHistoryUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HistoryUseCaseModule {
    @Provides
    @Singleton
    fun providesUpdateHistoryUseCase(
        repository: HistoryRepository,
        @IODispatcher ioDispatcher: CoroutineDispatcher
    ): UpdateHistoryUseCase {
        return UpdateHistoryUseCase(repository, ioDispatcher)
    }

    @Provides
    @Singleton
    fun providesGetHistoryUseCase(
        repository: HistoryRepository,
        cartRepository: CartRepository,
        @IODispatcher ioDispatcher: CoroutineDispatcher
    ): GetHistoryUseCase {
        return GetHistoryUseCase(repository, cartRepository, ioDispatcher)
    }

    @Provides
    @Singleton
    fun providesInsertHistoryUseCase(
        repository: HistoryRepository,
        @IODispatcher ioDispatcher: CoroutineDispatcher
    ): InsertHistoryUseCase {
        return InsertHistoryUseCase(repository, ioDispatcher)
    }
}