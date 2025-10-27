package com.rudra.eventreminder.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rudra.eventreminder.data.Event
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalDateTime

class CountdownViewModel : ViewModel() {

    private val _timeRemaining = MutableStateFlow(Duration.ZERO)
    val timeRemaining: StateFlow<Duration> = _timeRemaining

    fun startCountdown(event: Event) {
        viewModelScope.launch {
            while (true) {
                val now = LocalDateTime.now()
                val eventDateTime = LocalDateTime.of(event.date, event.reminderTimes.firstOrNull())
                val duration = Duration.between(now, eventDateTime)
                _timeRemaining.value = duration
                delay(1000)
            }
        }
    }
}
