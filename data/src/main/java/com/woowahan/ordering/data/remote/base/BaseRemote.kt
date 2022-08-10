package com.woowahan.ordering.data.remote.base

import retrofit2.Call

abstract class BaseRemote {
    fun <T> getResponse(call: Call<T>): T {
        return getError(call)
    }

    private fun <T> getError(call: Call<T>): T {
        return try {
            call.execute().body()!!
        } catch (e: Exception) {
            throw e
        }
    }
}