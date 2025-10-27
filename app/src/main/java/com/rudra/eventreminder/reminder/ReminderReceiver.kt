package com.rudra.eventreminder.reminder

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.rudra.eventreminder.FullScreenNotificationActivity
import com.rudra.eventreminder.R
import java.time.LocalDate
import java.time.LocalTime

class ReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val id = intent.getIntExtra("id", (System.currentTimeMillis() % Int.MAX_VALUE).toInt())
        val title = intent.getStringExtra("title") ?: "Event"
        val dateStr = intent.getStringExtra("date")
        val timeStr = intent.getStringExtra("reminderTime")
        val isRecurring = intent.getBooleanExtra("isRecurring", true)

        val fullScreenIntent = Intent(context, FullScreenNotificationActivity::class.java).apply {
            putExtra("title", title)
        }
        val fullScreenPendingIntent = PendingIntent.getActivity(context, id, fullScreenIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        val snoozeIntent = Intent(context, SnoozeReceiver::class.java).apply {
            putExtra("id", id)
            putExtra("title", title)
            putExtra("date", dateStr)
            putExtra("reminderTime", timeStr)
            putExtra("isRecurring", isRecurring)
        }
        val snoozePendingIntent = PendingIntent.getBroadcast(context, id, snoozeIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        val notif = NotificationCompat.Builder(context, "event_reminders")
            .setSmallIcon(R.drawable.ic_launcher_foreground) // make sure this exists
            .setContentTitle("Upcoming: $title")
            .setContentText(buildContent(dateStr, timeStr))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setFullScreenIntent(fullScreenPendingIntent, true)
            .setAutoCancel(true)
            .addAction(R.drawable.ic_launcher_foreground, "Snooze", snoozePendingIntent)
            .build()

        NotificationManagerCompat.from(context).notify(id, notif)

        // if recurring, compute next and reschedule
        if (isRecurring && dateStr != null) {
            try {
                val date = LocalDate.parse(dateStr)
                val time = timeStr?.let { LocalTime.parse(it) }
                // construct temp Event and reschedule for next year's occurrence
                val tempEvent = com.rudra.eventreminder.data.Event(
                    id = id,
                    title = title,
                    description = "", // Not needed for rescheduling
                    emoji = "", // Not needed for rescheduling
                    date = date,
                    reminderTimes = listOf(time!!),
                    isRecurring = true,
                    eventType = ""
                )
                ReminderScheduler.scheduleReminders(context, tempEvent)
            } catch (ex: Exception) {
                // ignore parse errors
            }
        }
    }

    private fun buildContent(dateStr: String?, timeStr: String?): String {
        return when {
            dateStr != null && timeStr != null -> "$dateStr at $timeStr"
            dateStr != null -> dateStr
            else -> "Reminder"
        }
    }
}
