package com.rudra.eventreminder.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.rudra.eventreminder.data.AppDatabase
import com.rudra.eventreminder.data.Habit
import com.rudra.eventreminder.repository.HabitRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class HabitViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: HabitRepository
    val habits: Flow<List<Habit>>

    init {
        val habitDao = AppDatabase.getDatabase(application).habitDao()
        repository = HabitRepository(habitDao)
        habits = repository.getAllHabits()
    }

    fun addHabit(habit: Habit) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addHabit(habit)
        }
    }

    fun updateHabit(habit: Habit) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateHabit(habit)
        }
    }

    fun deleteHabit(habit: Habit) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteHabit(habit)
        }
    }
}
