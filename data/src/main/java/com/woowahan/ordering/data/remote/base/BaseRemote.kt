package com.woowahan.ordering.data.remote.base

import retrofit2.Call

abstract class BaseRemote {
    fun <T> getResponse(call: Call<T>): T {
        return getError(call)
    }

    private fun <T> getError(call: Call<T>): T {
        val response =
            if (!call.isExecuted)
                call.execute()
            else
                call.clone().execute()
        return if (response.isSuccessful) {
            response.body()!!
        } else {
            throw Throwable("${response.code()}")
        }
    }
}