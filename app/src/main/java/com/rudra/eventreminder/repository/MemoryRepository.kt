package com.rudra.eventreminder.repository

import com.rudra.eventreminder.data.DAO.MemoryDao
import com.rudra.eventreminder.data.Memory
import kotlinx.coroutines.flow.Flow

class MemoryRepository(private val memoryDao: MemoryDao) {
    fun getAllMemories(): Flow<List<Memory>> {
        return memoryDao.getAllMemories()
    }

    fun getMemoriesForEvent(eventId: Int): Flow<List<Memory>> {
        return memoryDao.getMemoriesForEvent(eventId)
    }

    suspend fun addMemory(memory: Memory) {
        memoryDao.addMemory(memory)
    }

    suspend fun updateMemory(memory: Memory) {
        memoryDao.updateMemory(memory)
    }

    suspend fun deleteMemory(memory: Memory) {
        memoryDao.deleteMemory(memory)
    }
}
