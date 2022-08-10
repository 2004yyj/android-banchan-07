package com.woowahan.ordering.data.remote.service

import com.woowahan.ordering.data.entity.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface FoodService {
    @GET("best")
    fun getBestList(): Call<ListResponse<BestEntity>>

    @GET("main")
    fun getMainList(): Call<ListResponse<FoodEntity>>

    @GET("soup")
    fun getSoupList(): Call<ListResponse<FoodEntity>>

    @GET("side")
    fun getSideList(): Call<ListResponse<FoodEntity>>

    @GET("detail/{hash}")
    fun getFoodDetail(
        @Path("hash") hash: String
    ): Call<FoodDetailResponse>
}