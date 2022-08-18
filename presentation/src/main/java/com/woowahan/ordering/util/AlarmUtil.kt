package com.woowahan.ordering.util

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent


fun Context.startAlarmReceiver(intent: Intent, time: Long) {
    val alarmManager = getSystemService(AlarmManager::class.java)
    val pendingIntent = PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_IMMUTABLE)
    alarmManager[AlarmManager.RTC, time] = pendingIntent
}