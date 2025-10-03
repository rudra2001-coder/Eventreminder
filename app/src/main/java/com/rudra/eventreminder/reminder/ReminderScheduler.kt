package com.rudra.eventreminder.reminder



import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.rudra.eventreminder.data.Event
import java.time.LocalDateTime
import java.time.ZoneId

object ReminderScheduler {
    private const val CHANNEL_ID = "event_reminders"

    fun scheduleReminder(context: Context, event: Event) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, ReminderReceiver::class.java).apply {
            putExtra("id", event.id)
            putExtra("title", event.title)
            putExtra("date", event.date.toString())
            putExtra("reminderTime", event.reminderTime?.toString())
            putExtra("isRecurring", event.isRecurring)
        }
        val pending = PendingIntent.getBroadcast(
            context,
            event.id, // unique per event
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // compute next occurrence local date/time
        val nextDate = com.rudra.eventreminder.util.DateUtils.nextOccurrence(event.date, event.isRecurring)
        val time = event.reminderTime ?: java.time.LocalTime.of(9, 0)
        val ldt = LocalDateTime.of(nextDate, time)
        val millis = ldt.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, millis, pending)
    }

    fun cancelReminder(context: Context, eventId: Int) {
        val intent = Intent(context, ReminderReceiver::class.java)
        val pending = PendingIntent.getBroadcast(
            context,
            eventId,
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
