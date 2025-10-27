package com.rudra.eventreminder.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rudra.eventreminder.viewmodel.CountdownViewModel
import com.rudra.eventreminder.viewmodel.EventViewModel
import kotlinx.coroutines.flow.first

@Composable
fun CountdownScreen(viewModel: CountdownViewModel, eventId: Int, eventViewModel: EventViewModel) {
    val timeRemaining by viewModel.timeRemaining.collectAsState()
    val event by eventViewModel.getEvent(eventId.toLong()).collectAsState(initial = null)

    event?.let {
        viewModel.startCountdown(it)
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Text(text = event?.title ?: "Event")
            Spacer(modifier = Modifier.height(16.dp))
            CircularProgressIndicator()
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "${timeRemaining.toDays()} Days")
            Text(text = "${timeRemaining.toHours() % 24} Hours")
            Text(text = "${timeRemaining.toMinutes() % 60} Minutes")
            Text(text = "${timeRemaining.seconds % 60} Seconds")
        }
    }
}
