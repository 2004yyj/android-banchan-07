package com.woowahan.ordering.di.usecase

import com.woowahan.ordering.domain.repository.CartRepository
import com.woowahan.ordering.domain.repository.FoodRepository
import com.woowahan.ordering.domain.usecase.food.GetBestListUseCase
import com.woowahan.ordering.domain.usecase.food.GetFoodDetailUseCase
import com.woowahan.ordering.domain.usecase.food.GetMenuListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FoodUseCaseModule {
    @Provides
    @Singleton
    fun providesGetBestListUseCase(
        cartRepository: CartRepository,
        foodRepository: FoodRepository
    ): GetBestListUseCase {
        return GetBestListUseCase(cartRepository, foodRepository)
    }

    @Provides
    @Singleton
    fun providesGetMenuListUseCase(
        cartRepository: CartRepository,
        foodRepository: FoodRepository
    ): GetMenuListUseCase {
        return GetMenuListUseCase(cartRepository, foodRepository)
    }

    @Provides
    @Singleton
    fun providesGetFoodDetailUseCase(
        foodRepository: FoodRepository
    ): GetFoodDetailUseCase {
        return GetFoodDetailUseCase(foodRepository)
    }
}