package com.rudra.eventreminder.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "memories")
data class Memory(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val eventId: Int,
    val photoPath: String,
    val mood: String,
    val notes: String,
    val location: String? = null
)
