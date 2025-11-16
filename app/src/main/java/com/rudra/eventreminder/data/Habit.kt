package com.rudra.eventreminder.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "habits")
data class Habit(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val streak: Int = 0,
    val lastCompletedDate: LocalDate? = null
)
