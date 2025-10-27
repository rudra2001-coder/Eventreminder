package com.rudra.eventreminder.reminder

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.rudra.eventreminder.data.Event
import com.rudra.eventreminder.util.DateUtils
import java.time.LocalDate
import java.time.LocalTime

class SnoozeReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val eventId = intent.getIntExtra("id", 0)
        val title = intent.getStringExtra("title")
        val date = intent.getStringExtra("date")
        val reminderTime = intent.getStringExtra("reminderTime")
        val isRecurring = intent.getBooleanExtra("isRecurring", false)

        val event = Event(
            id = eventId,
            title = title ?: "",
            description = "",
            date = LocalDate.parse(date),
            eventType = "",
            emoji = "",
            reminderTimes = listOf(LocalTime.parse(reminderTime)),
            isRecurring = isRecurring
        )

        // Snooze for 10 minutes
        val snoozedEvent = event.copy(
            reminderTimes = listOf(LocalTime.now().plusMinutes(10))
        )

        ReminderScheduler.scheduleReminders(context, snoozedEvent)
    }
}
