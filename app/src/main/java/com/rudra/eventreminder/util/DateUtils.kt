package com.rudra.eventreminder.util



import java.time.LocalDate

object DateUtils {
    /**
     * Given an original date and whether it recurs yearly, compute the next occurrence:
     * - If non-recurring and date >= today => return date
     * - If recurring => return next month/day occurrence (this year or next)
     */
    fun nextOccurrence(original: LocalDate, recurring: Boolean): LocalDate {
        val today = LocalDate.now()
        if (!recurring) {
            return if (original.isAfter(today) || original.isEqual(today)) original else original
        }
        // recurring yearly: keep month/day, set year to current or next
        val candidate = original.withYear(today.year)
        return if (candidate.isBefore(today) || candidate.isEqual(today).not() && candidate.isBefore(today)) {
            candidate.plusYears(1)
        } else {
            // If candidate is today or in future this year, use it
            if (candidate.isBefore(today)) candidate.plusYears(1) else candidate
        }
    }
}
