package com.rudra.eventreminder.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import com.rudra.eventreminder.data.Event   // <-- add this

@Dao
interface EventDao {
    @Query("SELECT * FROM events ORDER BY date ASC")
    fun getAllFlow(): Flow<List<Event>>

    @Query("SELECT * FROM events ORDER BY date ASC")
    suspend fun getAllOnce(): List<Event>

    @Query("SELECT * FROM events WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): Event?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(event: Event): Long

    @Update
    suspend fun update(event: Event)

    @Delete
    suspend fun delete(event: Event)
}
