package com.rudra.eventreminder.data



    import androidx.room.Entity
    import androidx.room.PrimaryKey
    import java.time.LocalDate
    import java.time.LocalTime

    @Entity(tableName = "events")
    data class Event(
        @PrimaryKey(autoGenerate = true) val id: Int = 0,
        val title: String,
        val description: String? = null,
        val date: LocalDate,            // date (year of event; for recurring we use month+day)
        val reminderTime: LocalTime? = null, // optional time (e.g., 09:00)
        val isRecurring: Boolean = true,
        val isFavorite: Boolean = false
    )
