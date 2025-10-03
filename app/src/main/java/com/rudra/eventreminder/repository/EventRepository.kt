package com.rudra.eventreminder.repository



import com.rudra.eventreminder.data.Event
import com.rudra.eventreminder.data.EventDao
import kotlinx.coroutines.flow.Flow

class EventRepository(private val dao: EventDao) {
    fun allEventsFlow(): Flow<List<Event>> = dao.getAllFlow()
    suspend fun allEventsOnce(): List<Event> = dao.getAllOnce()
    suspend fun getEvent(id: Int): Event? = dao.getById(id)
    suspend fun insert(event: Event): Long = dao.insert(event)
    suspend fun update(event: Event) = dao.update(event)
    suspend fun delete(event: Event) = dao.delete(event)
}
