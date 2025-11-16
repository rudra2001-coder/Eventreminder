package com.rudra.eventreminder.data.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.rudra.eventreminder.data.Memory
import kotlinx.coroutines.flow.Flow

@Dao
interface MemoryDao {
    @Query("SELECT * FROM memories")
    fun getAllMemories(): Flow<List<Memory>>

    @Query("SELECT * FROM memories WHERE eventId = :eventId")
    fun getMemoriesForEvent(eventId: Int): Flow<List<Memory>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMemory(memory: Memory)

    @Update
    suspend fun updateMemory(memory: Memory)

    @Delete
    suspend fun deleteMemory(memory: Memory)
}
