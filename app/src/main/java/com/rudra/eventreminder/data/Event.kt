package com.rudra.eventreminder.data

import java.time.LocalDate
import java.time.LocalTime

data class Event(
    val id: Int = 0,
    val title: String,
    val description: String?,
    val date: LocalDate,
    val eventType: String, // Added for Smart Guessing
    val emoji: String,
    val reminderTimes: List<LocalTime>,
    val isRecurring: Boolean
)
