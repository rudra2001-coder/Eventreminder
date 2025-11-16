package com.rudra.eventreminder.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "gifts")
data class Gift(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val eventId: Int,
    val name: String,
    val budget: Double,
    val isPurchased: Boolean = false
)
