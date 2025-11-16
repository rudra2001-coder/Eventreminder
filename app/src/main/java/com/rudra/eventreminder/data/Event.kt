package com.rudra.eventreminder.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.time.LocalDate
import java.time.LocalTime

@Entity(tableName = "events")
@TypeConverters(Converters::class)
data class Event(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String?,
    val date: LocalDate,
    val eventType: String,
    val emoji: String,
    val reminderTimes: List<LocalTime>,
    val isRecurring: Boolean
)
