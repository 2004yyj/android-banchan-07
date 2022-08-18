package com.woowahan.ordering.ui.receiver

import android.app.Notification
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import com.woowahan.ordering.R
import com.woowahan.ordering.util.NotificationUtil.CART_CHANNEL_ID
import com.woowahan.ordering.util.executeRandom

class CartReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val food = intent.getStringExtra(FOOD_TITLE)
        val foodCount = intent.getIntExtra(FOOD_COUNT, 0) - 1
        val foodTitleWithCount = if (foodCount > 0) "$food 외 ${foodCount}개" else food

        val title = "배달이 완료되었어요!"
        val contentText = "고객님께서 주문하신 ${foodTitleWithCount}이(가) 배달되었어요."

        val notificationManager = context.getSystemService(NotificationManager::class.java)
        val notification =
            Notification.Builder(context, CART_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setColor(ContextCompat.getColor(context, R.color.primary_main))
                .setContentTitle(title)
                .setContentText(contentText)
                .setStyle(Notification.BigTextStyle().bigText(contentText))
                .build()
        notificationManager.notify(executeRandom(), notification)
    }

    companion object {
        const val FOOD_TITLE = "foodName"
        const val FOOD_COUNT = "foodCount"
        const val DELIVERY_FINISHED_TIME = "deliveryTime"
    }
}