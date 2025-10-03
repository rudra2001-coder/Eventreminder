package com.rudra.eventreminder.workers



import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.rudra.eventreminder.data.AppDatabase
import com.rudra.eventreminder.reminder.ReminderScheduler

class RescheduleWorker(appContext: Context, params: WorkerParameters): CoroutineWorker(appContext, params) {
    override suspend fun doWork(): Result {
        try {
            val dao = AppDatabase.getInstance(applicationContext).eventDao()
            val events = dao.getAllOnce()
            for (e in events) {
                if (e.reminderTime != null) {
                    ReminderScheduler.scheduleReminder(applicationContext, e)
                }
            }
            return Result.success()
        } catch (ex: Exception) {
            return Result.retry()
        }
    }
}
