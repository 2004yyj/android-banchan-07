package com.woowahan.ordering.data.remote.datasource

import com.woowahan.ordering.data.datasource.FoodDataSource
import com.woowahan.ordering.data.mapper.toModel
import com.woowahan.ordering.data.remote.base.BaseRemote
import com.woowahan.ordering.data.remote.service.FoodService
import com.woowahan.ordering.domain.model.Best
import com.woowahan.ordering.domain.model.Food
import com.woowahan.ordering.domain.model.FoodDetail
import javax.inject.Inject

class FoodDataSourceImpl @Inject constructor(
    private val service: FoodService
) : FoodDataSource, BaseRemote() {
    override fun getBestList(): List<Best> {
        return getResponse(service.getBestList()).body.map {
            it.toModel()
        }
    }

    override fun getMainList(): List<Food> {
        return getResponse(service.getMainList()).body.map {
            it.toModel()
        }
    }

    override fun getSoupList(): List<Food> {
        return getResponse(service.getSoupList()).body.map {
            it.toModel()
        }
    }

    override fun getSideList(): List<Food> {
        return getResponse(service.getSideList()).body.map {
            it.toModel()
        }
    }

    override fun getFoodDetail(hash: String): FoodDetail {
        return getResponse(service.getFoodDetail(hash)).toModel()
    }

}