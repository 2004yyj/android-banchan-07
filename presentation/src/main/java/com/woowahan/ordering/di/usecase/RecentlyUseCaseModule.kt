package com.woowahan.ordering.di.usecase

import com.woowahan.ordering.domain.repository.CartRepository
import com.woowahan.ordering.domain.repository.RecentlyRepository
import com.woowahan.ordering.domain.usecase.recently.GetRecentlyUseCase
import com.woowahan.ordering.domain.usecase.recently.InsertRecentlyUseCase
import com.woowahan.ordering.domain.usecase.recently.UpdateRecentlyUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RecentlyUseCaseModule {
    @Provides
    @Singleton
    fun providesUpdateRecentlyUseCase(repository: RecentlyRepository): UpdateRecentlyUseCase {
        return UpdateRecentlyUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesGetRecentlyUseCase(
        repository: RecentlyRepository,
        cartRepository: CartRepository
    ): GetRecentlyUseCase {
        return GetRecentlyUseCase(repository, cartRepository)
    }

    @Provides
    @Singleton
    fun providesInsertRecentlyUseCase(repository: RecentlyRepository): InsertRecentlyUseCase {
        return InsertRecentlyUseCase(repository)
    }
}