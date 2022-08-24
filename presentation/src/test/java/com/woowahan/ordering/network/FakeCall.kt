package com.woowahan.ordering.network

import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class FakeCall<T>: Call<T> {
    abstract override fun execute(): Response<T>

    override fun clone(): Call<T> {
        return this
    }

    override fun enqueue(callback: Callback<T>) {}

    override fun isExecuted(): Boolean {
        return false
    }

    override fun cancel() {}

    override fun isCanceled(): Boolean {
        return false
    }

    override fun request(): Request {
        return Request.Builder().build()
    }

    override fun timeout(): Timeout {
        return Timeout.NONE
    }
}