package com.rudra.eventreminder.utils

import java.util.concurrent.TimeUnit

fun getDaysLeft(eventDateMillis: Long): Long {
    val currentTimeMillis = System.currentTimeMillis()
    val diff = eventDateMillis - currentTimeMillis
    return if (diff > 0) {
        TimeUnit.MILLISECONDS.toDays(diff)
    } else {
        0
    }
}
