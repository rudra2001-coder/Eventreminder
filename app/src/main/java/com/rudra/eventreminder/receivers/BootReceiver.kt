package com.rudra.eventreminder.receivers



import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.rudra.eventreminder.workers.RescheduleWorker

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            val work = OneTimeWorkRequestBuilder<RescheduleWorker>().build()
            WorkManager.getInstance(context)
                .enqueueUniqueWork("reschedule_reminders", ExistingWorkPolicy.REPLACE, work)
        }
    }
}
