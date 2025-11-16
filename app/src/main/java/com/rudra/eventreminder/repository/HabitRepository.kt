package com.rudra.eventreminder.repository

import com.rudra.eventreminder.data.DAO.HabitDao
import com.rudra.eventreminder.data.Habit
import kotlinx.coroutines.flow.Flow

class HabitRepository(private val habitDao: HabitDao) {
    fun getAllHabits(): Flow<List<Habit>> {
        return habitDao.getAllHabits()
    }

    suspend fun addHabit(habit: Habit) {
        habitDao.addHabit(habit)
    }

    suspend fun updateHabit(habit: Habit) {
        habitDao.updateHabit(habit)
    }

    suspend fun deleteHabit(habit: Habit) {
        habitDao.deleteHabit(habit)
    }
}
