package com.woowahan.ordering.data.datasource

import com.woowahan.ordering.domain.model.Best
import com.woowahan.ordering.domain.model.Food
import com.woowahan.ordering.domain.model.FoodDetail

interface FoodDataSource {
    fun getBestList(): List<Best>
    fun getMainList(): List<Food>
    fun getSoupList(): List<Food>
    fun getSideList(): List<Food>
    fun getFoodDetail(hash: String): FoodDetail
}