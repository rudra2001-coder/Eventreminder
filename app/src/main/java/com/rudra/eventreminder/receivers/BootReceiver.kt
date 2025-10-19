package com.rudra.eventreminder.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.rudra.eventreminder.data.AppDatabase
import com.rudra.eventreminder.reminder.ReminderScheduler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            val pendingResult = goAsync()
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val dao = AppDatabase.getDatabase(context).eventDao()
                    val events = dao.getAllEvents().first()
                    events.forEach { event ->
                        if (event.reminderTime != null) {
                            ReminderScheduler.scheduleReminder(context, event)
                        }
                    }
                } finally {
                    pendingResult.finish()
                }
            }
        }
    }
}
