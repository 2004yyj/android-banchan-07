package com.woowahan.ordering.di.usecase

import com.woowahan.ordering.domain.repository.CartRepository
import com.woowahan.ordering.domain.repository.HistoryRepository
import com.woowahan.ordering.domain.usecase.history.GetHistoryUseCase
import com.woowahan.ordering.domain.usecase.history.InsertHistoryUseCase
import com.woowahan.ordering.domain.usecase.history.UpdateHistoryUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HistoryUseCaseModule {
    @Provides
    @Singleton
    fun providesUpdateHistoryUseCase(repository: HistoryRepository): UpdateHistoryUseCase {
        return UpdateHistoryUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesGetHistoryUseCase(
        repository: HistoryRepository,
        cartRepository: CartRepository
    ): GetHistoryUseCase {
        return GetHistoryUseCase(repository, cartRepository)
    }

    @Provides
    @Singleton
    fun providesInsertHistoryUseCase(repository: HistoryRepository): InsertHistoryUseCase {
        return InsertHistoryUseCase(repository)
    }
}