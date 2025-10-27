package com.rudra.eventreminder.repository

import com.rudra.eventreminder.data.DAO.EventDao
import com.rudra.eventreminder.data.Event
import kotlinx.coroutines.flow.Flow

class EventRepository(private val eventDao: EventDao) {
    val getAllEvents: Flow<List<Event>> = eventDao.getAllEvents()

    fun getEventById(id: Long): Flow<Event> {
        return eventDao.getEventById(id)
    }

    suspend fun addEvent(event: Event): Long {
        return eventDao.addEvent(event)
    }

    suspend fun updateEvent(event: Event) {
        eventDao.updateEvent(event)
    }

    suspend fun deleteEvent(event: Event) {
        eventDao.deleteEvent(event)
    }
}
