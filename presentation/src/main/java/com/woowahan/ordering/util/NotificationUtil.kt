package com.woowahan.ordering.util

import android.app.Notification
import android.app.Notification.VISIBILITY_PRIVATE
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.app.PendingIntent
import android.content.Context
import androidx.core.content.ContextCompat
import com.woowahan.ordering.R

class NotificationUtil(
    private val notificationManager: NotificationManager,
    private val context: Context,
    private val channelId: String,
    private val channelName: String,
) {

    fun createChannel() {
        val channel = NotificationChannel(channelId, channelName, IMPORTANCE_HIGH)
        channel.enableLights(true)
        channel.enableVibration(true)
        channel.lightColor = R.color.primary_main
        channel.lockscreenVisibility = VISIBILITY_PRIVATE
        notificationManager.createNotificationChannel(channel)
    }

    fun createNotification(
        title: String,
        content: String,
        pendingIntent: PendingIntent
    ): Notification {
        return Notification.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_notification)
            .setColor(ContextCompat.getColor(context, R.color.primary_main))
            .setContentTitle(title)
            .setContentText(content)
            .setStyle(Notification.BigTextStyle().bigText(content))
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
    }
}