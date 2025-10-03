package com.rudra.eventreminder.ui



import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rudra.eventreminder.data.Event
import com.rudra.eventreminder.viewmodel.EventViewModel
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(viewModel: EventViewModel, onAddClick: () -> Unit) {
    val events by viewModel.events.collectAsState()
    Scaffold(
        topBar = { CenterAlignedTopAppBar(title = { Text("Event Reminder") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(events) { e ->
                EventCard(event = e, onClick = { /* open edit if you want */ })
            }
        }
    }
}

@Composable
fun EventCard(event: Event, onClick: () -> Unit) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .clickable { onClick() },
        shape = MaterialTheme.shapes.medium) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = event.title, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(4.dp))
            val fmt = DateTimeFormatter.ofPattern("MMM dd, yyyy")
            Text(text = "Date: ${event.date.format(fmt)}")
            event.reminderTime?.let {
                Text(text = "Reminder: ${it.toString()}")
            }
        }
    }
}
