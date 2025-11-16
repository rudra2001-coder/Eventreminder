package com.rudra.eventreminder.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val eventId: Int,
    val title: String,
    val content: String,
    val attachmentPath: String? = null
)
