package com.woowahan.ordering.network

import android.content.Context
import android.util.Log
import androidx.annotation.WorkerThread
import com.woowahan.ordering.util.hasNetwork
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket
import kotlin.jvm.Throws

class ConnectionInterceptor(private val context: Context): Interceptor {
    @WorkerThread
    private fun isInternetAvailable(): Boolean {
        return try {
            val timeoutMs = 1500
            val sock = Socket()
            val address = InetSocketAddress("8.8.8.8", 53)
            sock.connect(address, timeoutMs)
            sock.close()
            true
        } catch (e: IOException) {
            false
        }
    }

    @Throws(Exception::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = if (context.hasNetwork() && isInternetAvailable())
            request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
        else {
            println("intercept")
            throw NoInternetConnectionException()
        }
        return chain.proceed(request)
    }

    class NoInternetConnectionException(override val message: String = ""): IOException()
}