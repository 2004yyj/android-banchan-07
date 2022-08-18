package com.woowahan.ordering.application

import android.app.Application
import android.app.NotificationManager
import com.woowahan.ordering.util.NotificationUtil.CART_CHANNEL_ID
import com.woowahan.ordering.util.NotificationUtil.CART_CHANNEL_NAME
import com.woowahan.ordering.util.createChannel
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class HiltApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.createChannel(CART_CHANNEL_ID, CART_CHANNEL_NAME)
    }
}