package com.woowahan.ordering.data.entity

import com.google.gson.annotations.SerializedName

data class ListResponse<T>(
    @SerializedName("status_code")
    val statusCode: Int,
    @SerializedName("body")
    val body: T
)