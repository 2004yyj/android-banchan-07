package com.woowahan.ordering.di.usecase

import com.woowahan.ordering.di.scope.IODispatcher
import com.woowahan.ordering.domain.repository.CartRepository
import com.woowahan.ordering.domain.repository.FoodRepository
import com.woowahan.ordering.domain.usecase.food.GetBestListUseCase
import com.woowahan.ordering.domain.usecase.food.GetFoodDetailUseCase
import com.woowahan.ordering.domain.usecase.food.GetMenuListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FoodUseCaseModule {
    @Provides
    @Singleton
    fun providesGetBestListUseCase(
        cartRepository: CartRepository,
        foodRepository: FoodRepository,
        @IODispatcher ioDispatcher: CoroutineDispatcher
    ): GetBestListUseCase {
        return GetBestListUseCase(cartRepository, foodRepository, ioDispatcher)
    }

    @Provides
    @Singleton
    fun providesGetMenuListUseCase(
        cartRepository: CartRepository,
        foodRepository: FoodRepository,
        @IODispatcher ioDispatcher: CoroutineDispatcher
    ): GetMenuListUseCase {
        return GetMenuListUseCase(cartRepository, foodRepository, ioDispatcher)
    }

    @Provides
    @Singleton
    fun providesGetFoodDetailUseCase(
        foodRepository: FoodRepository,
        @IODispatcher ioDispatcher: CoroutineDispatcher
    ): GetFoodDetailUseCase {
        return GetFoodDetailUseCase(foodRepository, ioDispatcher)
    }
}