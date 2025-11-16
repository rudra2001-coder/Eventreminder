package com.rudra.eventreminder.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Title
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.rudra.eventreminder.data.Event
import com.rudra.eventreminder.viewmodel.EventViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetailScreen(
    viewModel: EventViewModel,
    eventId: Long,
    onEdit: (Long) -> Unit,
    onDelete: (Long) -> Unit,
    onBack: () -> Unit
) {
    val event by viewModel.getEvent(eventId).collectAsState(initial = null)
    var showDeleteDialog by remember { mutableStateOf(false) }

    // Delete Confirmation Dialog
    if (showDeleteDialog) {
        DeleteConfirmationDialog(
            onConfirm = {
                onDelete(eventId)
                showDeleteDialog = false
            },
            onDismiss = { showDeleteDialog = false }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Event Details",
                        fontWeight = FontWeight.SemiBold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                navigationIcon = {
                    IconButton(onClick = { onBack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { showDeleteDialog = true },
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete Event",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            )
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Button(
                    onClick = { onEdit(eventId) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                    Text("Edit Event")
                }
            }
        }
    ) { paddingValues ->
        event?.let { eventDetails ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
                    .background(MaterialTheme.colorScheme.background)
            ) {
                // Header with status
                EventStatusHeader(eventDetails)

                Spacer(modifier = Modifier.height(24.dp))

                // Event Details Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(modifier = Modifier.padding(24.dp)) {
                        // Title Section
                        DetailItem(
                            icon = Icons.Default.Title,
                            title = "Title",
                            content = eventDetails.title,
                            iconColor = MaterialTheme.colorScheme.primary
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        // Description Section
                        DetailItem(
                            icon = Icons.Default.Description,
                            title = "Description",
                            content = eventDetails.description ?: "No description",
                            iconColor = MaterialTheme.colorScheme.secondary,
                            isMultiline = true
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        // Date and Time Section
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            // Date
                            Column(modifier = Modifier.weight(1f)) {
                                DetailItem(
                                    icon = Icons.Default.CalendarMonth,
                                    title = "Date",
                                    content = formatDisplayDate(eventDetails.date),
                                    iconColor = MaterialTheme.colorScheme.tertiary,
                                    compact = true
                                )
                            }

                            // Time
                            Column(modifier = Modifier.weight(1f)) {
                                DetailItem(
                                    icon = Icons.Default.Notifications,
                                    title = "Time",
                                    content = eventDetails.reminderTimes.firstOrNull()?.format(DateTimeFormatter.ofPattern("hh:mm a")) ?: "Not set",
                                    iconColor = MaterialTheme.colorScheme.tertiary,
                                    compact = true
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Additional Info Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Event Information",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        InfoRow(
                            label = "Days remaining:",
                            value = calculateDaysRemaining(eventDetails.date).toString()
                        )

                        InfoRow(
                            label = "Status:",
                            value = getEventStatus(eventDetails.date)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        } ?: run {
            // Loading or not found state
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (eventId == -1L) "No event selected" else "Event not found",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}

@Composable
private fun EventStatusHeader(eventDetails: Event) {
    val daysRemaining = calculateDaysRemaining(eventDetails.date)
    val statusColor = when {
        daysRemaining == 0L -> MaterialTheme.colorScheme.primary
        daysRemaining < 0L -> MaterialTheme.colorScheme.error
        else -> MaterialTheme.colorScheme.secondary
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(statusColor.copy(alpha = 0.1f))
            .padding(vertical = 20.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = when {
                    daysRemaining == 0L -> "🎉 Today's Event!"
                    daysRemaining < 0L -> "✅ Event Completed"
                    else -> "📅 Upcoming Event"
                },
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = statusColor
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = when {
                    daysRemaining == 0L -> "Happening today!"
                    daysRemaining < 0L -> "This event has passed"
                    else -> "$daysRemaining days remaining"
                },
                style = MaterialTheme.typography.bodyMedium,
                color = statusColor.copy(alpha = 0.8f)
            )
        }
    }
}

@Composable
private fun DetailItem(
    icon: ImageVector,
    title: String,
    content: String,
    iconColor: Color,
    isMultiline: Boolean = false,
    compact: Boolean = false
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = if (isMultiline) Alignment.Top else Alignment.CenterVertically
    ) {
        // Icon
        Box(
            modifier = Modifier
                .size(if (compact) 36.dp else 40.dp)
                .clip(CircleShape)
                .background(iconColor.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = iconColor,
                modifier = Modifier.size(if (compact) 18.dp else 20.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Content
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = content,
                style = if (compact) MaterialTheme.typography.bodyMedium
                else MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = if (compact) FontWeight.Normal else FontWeight.SemiBold,
                maxLines = if (isMultiline) Int.MAX_VALUE else 1,
                textAlign = if (isMultiline) TextAlign.Start else TextAlign.Start
            )
        }
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun DeleteConfirmationDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Delete Event") },
        text = { Text("Are you sure you want to delete this event? This action cannot be undone.") },
        confirmButton = {
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text("Delete")
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.outline
                )
            ) {
                Text("Cancel")
            }
        }
    )
}

// Helper functions
private fun formatDisplayDate(date: LocalDate): String {
    val formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy")
    return date.format(formatter)
}

private fun calculateDaysRemaining(eventDate: LocalDate): Long {
    return ChronoUnit.DAYS.between(LocalDate.now(), eventDate)
}

private fun getEventStatus(eventDate: LocalDate): String {
    val daysRemaining = calculateDaysRemaining(eventDate)
    return when {
        daysRemaining == 0L -> "Today"
        daysRemaining < 0L -> "Completed"
        daysRemaining == 1L -> "Tomorrow"
        else -> "Upcoming"
    }
}
