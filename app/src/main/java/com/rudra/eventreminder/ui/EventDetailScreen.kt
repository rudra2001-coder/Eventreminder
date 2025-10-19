package com.rudra.eventreminder.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rudra.eventreminder.viewmodel.EventViewModel

@Composable
fun EventDetailScreen(viewModel: EventViewModel, eventId: Long, onEdit: (Long) -> Unit) {
    val event by viewModel.getEvent(eventId).collectAsState(initial = null)

    event?.let { eventDetails ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(text = "Title: ${eventDetails.title}")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Description: ${eventDetails.description}")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Date: ${eventDetails.date}")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Time: ${eventDetails.reminderTime}")
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = { onEdit(eventDetails.id.toLong()) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Edit")
            }
        }
    }
}
