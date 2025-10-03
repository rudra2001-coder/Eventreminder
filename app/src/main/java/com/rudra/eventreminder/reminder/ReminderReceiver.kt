package com.rudra.eventreminder.reminder



import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
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

        val notif = NotificationCompat.Builder(context, "event_reminders")
            .setSmallIcon(R.drawable.ic_event) // make sure this exists
            .setContentTitle("Upcoming: $title")
            .setContentText(buildContent(dateStr, timeStr))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
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
                    date = date,
                    reminderTime = time,
                    isRecurring = true
                )
                ReminderScheduler.scheduleReminder(context, tempEvent)
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
