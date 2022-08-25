package com.woowahan.ordering.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.util.Log
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket

fun Context.hasNetwork(): Boolean {
    val connectivityManager: ConnectivityManager =
        getSystemService(ConnectivityManager::class.java)
    val network: Network = connectivityManager.activeNetwork ?: return false
    val actNetwork: NetworkCapabilities =
        connectivityManager.getNetworkCapabilities(network) ?: return false

    return actNetwork.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
        actNetwork.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
}