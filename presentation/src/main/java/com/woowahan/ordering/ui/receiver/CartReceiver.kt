package com.woowahan.ordering.ui.receiver

import android.app.Notification
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import com.woowahan.ordering.R
import com.woowahan.ordering.util.createChannel
import com.woowahan.ordering.util.executeRandom

class CartReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val food = intent.getStringExtra(FOOD_TITLE)
        val foodCount = intent.getIntExtra(FOOD_COUNT, 0) - 1
        val content =
            if (foodCount > 0)
                context.getString(R.string.cart_notification_content_with_count, food, foodCount)
            else
                context.getString(R.string.cart_notification_content, food)

        val channelId = context.getString(R.string.cart_channel_id)
        val channelName = context.getString(R.string.cart_channel_name)

        val notificationManager = context.getSystemService(NotificationManager::class.java)
        notificationManager.createChannel(channelId, channelName)
        val notification =
            Notification.Builder(context, channelId)
                .setSmallIcon(R.drawable.ic_notification)
                .setColor(ContextCompat.getColor(context, R.color.primary_main))
                .setContentTitle(context.getString(R.string.delivery_success))
                .setContentText(content)
                .setStyle(Notification.BigTextStyle().bigText(content))
                .build()
        notificationManager.notify(executeRandom(), notification)
    }

    companion object {
        const val FOOD_TITLE = "foodName"
        const val FOOD_COUNT = "foodCount"
        const val DELIVERY_FINISHED_TIME = "deliveryTime"
    }
}