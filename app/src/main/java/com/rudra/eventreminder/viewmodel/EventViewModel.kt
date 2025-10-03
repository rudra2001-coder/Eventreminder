package com.rudra.eventreminder.viewmodel



import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.rudra.eventreminder.data.Event
import com.rudra.eventreminder.data.AppDatabase
import com.rudra.eventreminder.repository.EventRepository
import com.rudra.eventreminder.reminder.ReminderScheduler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class EventViewModel(application: Application) : AndroidViewModel(application) {
    private val repo = EventRepository(AppDatabase.getInstance(application).eventDao())

    val events = repo.allEventsFlow().stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        emptyList()
    )

    fun addEvent(event: Event) = viewModelScope.launch(Dispatchers.IO) {
        val id = repo.insert(event).toInt()
        // schedule if reminder time set
        if (event.reminderTime != null) {
            val eventWithId = event.copy(id = id)
            ReminderScheduler.scheduleReminder(getApplication(), eventWithId)
        }
    }

    fun updateEvent(event: Event) = viewModelScope.launch(Dispatchers.IO) {
        repo.update(event)
        // reschedule: cancel then schedule if reminder exists
        ReminderScheduler.cancelReminder(getApplication(), event.id)
        if (event.reminderTime != null) {
            ReminderScheduler.scheduleReminder(getApplication(), event)
        }
    }

    fun deleteEvent(event: Event) = viewModelScope.launch(Dispatchers.IO) {
        repo.delete(event)
        ReminderScheduler.cancelReminder(getApplication(), event.id)
    }

    // reschedule all (used by BootWorker)
    suspend fun rescheduleAll() {
        val list = repo.allEventsOnce()
        for (e in list) {
            if (e.reminderTime != null) {
                ReminderScheduler.scheduleReminder(getApplication(), e)
            }
        }
    }
}
