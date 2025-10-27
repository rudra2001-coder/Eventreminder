package com.rudra.eventreminder.reminder

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.rudra.eventreminder.data.Event
import com.rudra.eventreminder.util.DateUtils
import java.time.LocalDateTime
import java.time.ZoneId

object ReminderScheduler {
    fun scheduleReminders(context: Context, event: Event) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        for (reminderTime in event.reminderTimes) {
            val intent = Intent(context, ReminderReceiver::class.java).apply {
                putExtra("id", event.id)
                putExtra("title", event.title)
                putExtra("date", event.date.toString())
                putExtra("reminderTime", reminderTime.toString())
                putExtra("isRecurring", event.isRecurring)
            }
            val pending = PendingIntent.getBroadcast(
                context,
                event.id + reminderTime.hashCode(), // unique per event and time
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            // compute next occurrence local date/time
            val nextDate = DateUtils.nextOccurrence(event.date, event.isRecurring)
            val ldt = LocalDateTime.of(nextDate, reminderTime)
            val millis = ldt.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, millis, pending)
        }
    }

    fun cancelReminders(context: Context, event: Event) {
        for (reminderTime in event.reminderTimes) {
            val intent = Intent(context, ReminderReceiver::class.java)
            val pending = PendingIntent.getBroadcast(
                context,
                event.id + reminderTime.hashCode(),
                intent,
                PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
            )
            if (pending != null) {
                val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                alarmManager.cancel(pending)
                pending.cancel()
            }
        }
    }
}
