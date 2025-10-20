package com.rudra.eventreminder.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.rudra.eventreminder.data.Event
import com.rudra.eventreminder.util.DateUtils
import com.rudra.eventreminder.viewmodel.EventViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PastEventsScreen(viewModel: EventViewModel, onNavigateBack: () -> Unit) {
    val pastEvents by viewModel.events.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Past Events", style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 8.dp)
        ) {
            items(pastEvents.filter { DateUtils.daysLeft(it.date, it.isRecurring) < 0 }) { event ->
                EventCountdownCard(
                    event = event,
                    daysLeft = DateUtils.daysLeft(event.date, event.isRecurring),
                    onDelete = { viewModel.deleteEvent(event) }
                )
            }
        }
    }
}
