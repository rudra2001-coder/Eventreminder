package com.rudra.eventreminder.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.rudra.eventreminder.data.AppDatabase
import com.rudra.eventreminder.data.Memory
import com.rudra.eventreminder.repository.MemoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MemoryViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: MemoryRepository
    val memories: Flow<List<Memory>>

    init {
        val memoryDao = AppDatabase.getDatabase(application).memoryDao()
        repository = MemoryRepository(memoryDao)
        memories = repository.getAllMemories()
    }

    fun getMemoriesForEvent(eventId: Int): Flow<List<Memory>> {
        return repository.getMemoriesForEvent(eventId)
    }

    fun addMemory(memory: Memory) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addMemory(memory)
        }
    }

    fun updateMemory(memory: Memory) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateMemory(memory)
        }
    }

    fun deleteMemory(memory: Memory) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteMemory(memory)
        }
    }
}
