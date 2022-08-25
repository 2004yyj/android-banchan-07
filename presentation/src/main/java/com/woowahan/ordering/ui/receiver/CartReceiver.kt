package com.woowahan.ordering.ui.receiver

import android.app.NotificationManager
import android.app.PendingIntent.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.woowahan.ordering.R
import com.woowahan.ordering.ui.worker.DeliveryTimeWorker
import com.woowahan.ordering.ui.activity.MainActivity
import com.woowahan.ordering.util.NotificationUtil
import com.woowahan.ordering.util.executeRandom

class CartReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val food = intent.getStringExtra(FOOD_TITLE)
        val foodCount = intent.getIntExtra(FOOD_COUNT, 0) - 1
        val deliveryTime = intent.getLongExtra(DELIVERY_FINISHED_TIME, 0L)
        callWorker(context, deliveryTime)

        val title = context.getString(R.string.delivery_success)
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
        val pendingIntent =
            getActivity(context, executeRandom(), mainIntent, FLAG_IMMUTABLE or FLAG_UPDATE_CURRENT)
        val notificationUtil =
            NotificationUtil(notificationManager, context, channelId, channelName)
        notificationUtil.createChannel()
        val notification = notificationUtil.createNotification(title, content, pendingIntent)
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
