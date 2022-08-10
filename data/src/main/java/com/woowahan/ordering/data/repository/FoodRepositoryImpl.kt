package com.woowahan.ordering.data.repository

import com.woowahan.ordering.data.datasource.FoodDataSource
import com.woowahan.ordering.domain.model.Best
import com.woowahan.ordering.domain.model.Food
import com.woowahan.ordering.domain.model.FoodDetail
import com.woowahan.ordering.domain.repository.FoodRepository
import javax.inject.Inject

class FoodRepositoryImpl @Inject constructor(
    private val dataSource: FoodDataSource
) : FoodRepository {
    override fun getBestList(): List<Best> {
        return dataSource.getBestList()
    }

    override fun getMainList(): List<Food> {
        return dataSource.getMainList()
    }

    override fun getSoupList(): List<Food> {
        return dataSource.getSoupList()
    }

    override fun getSideList(): List<Food> {
        return dataSource.getSideList()
    }

    override fun getFoodDetail(hash: String): FoodDetail {
        return dataSource.getFoodDetail(hash)
    }
}