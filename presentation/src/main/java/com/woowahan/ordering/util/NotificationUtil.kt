package com.woowahan.ordering.util

import android.app.Notification.VISIBILITY_PRIVATE
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_HIGH
import com.woowahan.ordering.R

fun NotificationManager.createChannel(channelId: String, channelName: String) {
    val channel = NotificationChannel(channelId, channelName, IMPORTANCE_HIGH)
    channel.enableLights(true)
    channel.enableVibration(true)
    channel.lightColor = R.color.primary_main
    channel.lockscreenVisibility = VISIBILITY_PRIVATE
    createNotificationChannel(channel)
}

object NotificationUtil {
    const val CART_CHANNEL_ID = "cart"
    const val CART_CHANNEL_NAME = "장바구니"
}