package com.woowahan.ordering.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities

fun Context.hasNetwork(): Boolean {
    val connectivityManager: ConnectivityManager =
        getSystemService(ConnectivityManager::class.java)
    val network: Network = connectivityManager.activeNetwork ?: return false
    val actNetwork: NetworkCapabilities =
        connectivityManager.getNetworkCapabilities(network) ?: return false

    return when {
        actNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        actNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        else -> false
    }
}