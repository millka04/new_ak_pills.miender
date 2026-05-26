package com.example.real_ak_pilsmineder.presentation.receiver

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.real_ak_pilsmineder.R


class ReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val name = intent.getStringExtra("med_name") ?: "Лекарство"

        val notification = NotificationCompat.Builder(context, "med_channel")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("Время принять лекарство")
            .setContentText(name)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify((System.currentTimeMillis() % 10000).toInt(), notification)
    }
}