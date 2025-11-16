package com.rudra.eventreminder.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.rudra.eventreminder.data.AppDatabase
import com.rudra.eventreminder.data.Event
import com.rudra.eventreminder.reminder.ReminderScheduler
import com.rudra.eventreminder.repository.EventRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class EventViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: EventRepository
    val events: Flow<List<Event>>

    init {
        val dao = AppDatabase.getDatabase(application).eventDao()
        repository = EventRepository(dao)
        events = repository.getAllEvents
    }

    fun getEvent(id: Long): Flow<Event> {
        return repository.getEventById(id)
    }

    fun addEvent(event: Event) = viewModelScope.launch(Dispatchers.IO) {
        val newEventId = repository.addEvent(event)
        val newEvent = repository.getEventById(newEventId).first()
        ReminderScheduler.scheduleReminders(getApplication(), newEvent)
    }

    fun updateEvent(event: Event) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateEvent(event)
        ReminderScheduler.cancelReminders(getApplication(), event)
        ReminderScheduler.scheduleReminders(getApplication(), event)
    }

    fun deleteEvent(eventId: Long) = viewModelScope.launch(Dispatchers.IO) {
        val event = repository.getEventById(eventId).first()
        repository.deleteEvent(event)
        ReminderScheduler.cancelReminders(getApplication(), event)
    }
}
