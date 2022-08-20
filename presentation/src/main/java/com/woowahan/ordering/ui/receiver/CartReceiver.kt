package com.woowahan.ordering.ui.receiver

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.content.ContextCompat
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.woowahan.ordering.R
import com.woowahan.ordering.ui.worker.DeliveryTimeWorker
import com.woowahan.ordering.ui.activity.MainActivity
import com.woowahan.ordering.util.createChannel
import com.woowahan.ordering.util.executeRandom

class CartReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val food = intent.getStringExtra(FOOD_TITLE)
        val foodCount = intent.getIntExtra(FOOD_COUNT, 0) - 1
        val deliveryTime = intent.getLongExtra(DELIVERY_FINISHED_TIME, 0L)
        callWorker(context, deliveryTime)
        
        val content =
            if (foodCount > 0)
                context.getString(R.string.cart_notification_content_with_count, food, foodCount)
            else
                context.getString(R.string.cart_notification_content, food)

        val channelId = context.getString(R.string.cart_channel_id)
        val channelName = context.getString(R.string.cart_channel_name)

        val notificationManager = context.getSystemService(NotificationManager::class.java)
        val mainIntent = Intent(context, MainActivity::class.java).apply {
            putExtra(DELIVERY_FINISHED_TIME, deliveryTime)
        }

        val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            getActivity(context, executeRandom(), mainIntent, FLAG_IMMUTABLE or FLAG_UPDATE_CURRENT)
        } else {
            getActivity(context, executeRandom(), mainIntent, FLAG_UPDATE_CURRENT)
        }
        notificationManager.createChannel(channelId, channelName)
        val notification =
            Notification.Builder(context, channelId)
                .setSmallIcon(R.drawable.ic_notification)
                .setColor(ContextCompat.getColor(context, R.color.primary_main))
                .setContentTitle(context.getString(R.string.delivery_success))
                .setContentText(content)
                .setStyle(Notification.BigTextStyle().bigText(content))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build()
        notificationManager.notify(executeRandom(), notification)
    }

    private fun callWorker(context: Context, deliveryTime: Long) {
        val data = Data.Builder()
            .putLong(DELIVERY_FINISHED_TIME, deliveryTime)
            .build()

        val deliveryWorkRequest = OneTimeWorkRequestBuilder<DeliveryTimeWorker>()
            .addTag("${deliveryTime}")
            .setInputData(data)
            .build()

        WorkManager
            .getInstance(context)
            .enqueue(deliveryWorkRequest)
    }

    companion object {
        const val FOOD_TITLE = "foodName"
        const val FOOD_COUNT = "foodCount"
        const val DELIVERY_FINISHED_TIME = "deliveryTime"
    }
}
