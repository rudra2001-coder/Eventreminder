package com.rudra.eventreminder.util

import java.time.LocalDate
import java.time.temporal.ChronoUnit

object DateUtils {
    fun nextOccurrence(eventDate: LocalDate, isRecurring: Boolean): LocalDate {
        val today = LocalDate.now()
        if (!isRecurring) {
            return eventDate
        }

        val eventThisYear = eventDate.withYear(today.year)
        return if (eventThisYear.isBefore(today)) {
            eventThisYear.plusYears(1)
        } else {
            eventThisYear
        }
    }

    fun daysLeft(eventDate: LocalDate, isRecurring: Boolean): Long {
        val next = nextOccurrence(eventDate, isRecurring)
        return ChronoUnit.DAYS.between(LocalDate.now(), next)
    }
}
