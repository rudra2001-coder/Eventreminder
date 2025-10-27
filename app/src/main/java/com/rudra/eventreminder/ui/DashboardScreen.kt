package com.rudra.eventreminder.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rudra.eventreminder.data.Event
import com.rudra.eventreminder.ui.theme.Theme
import com.rudra.eventreminder.viewmodel.BackupViewModel
import com.rudra.eventreminder.viewmodel.EventViewModel
import com.rudra.eventreminder.viewmodel.ThemeViewModel
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

enum class SortOption {
    DATE,
    TITLE,
    EVENT_TYPE
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: EventViewModel,
    themeViewModel: ThemeViewModel,
    backupViewModel: BackupViewModel,
    onEventClick: (Event) -> Unit,
    onAddEvent: () -> Unit,
    onNavigateToTimeline: () -> Unit
) {
    val events by viewModel.events.collectAsState(initial = emptyList())
    var searchQuery by remember { mutableStateOf("") }
    var sortOption by remember { mutableStateOf(SortOption.DATE) }

    val filteredEvents = events.filter { event ->
        event.title.contains(searchQuery, ignoreCase = true) ||
                !event.description.isNullOrBlank() && event.description.contains(searchQuery, ignoreCase = true)
    }

    val sortedEvents = when (sortOption) {
        SortOption.DATE -> filteredEvents.sortedBy { it.date }
        SortOption.TITLE -> filteredEvents.sortedBy { it.title }
        SortOption.EVENT_TYPE -> filteredEvents.sortedBy { it.eventType }
    }

    val upcomingEvents = sortedEvents
    val todayEvents = upcomingEvents.filter {
        it.date == LocalDate.now()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Events",
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                actions = {
                    var showSearch by remember { mutableStateOf(false) }
                    if (showSearch) {
                        TextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            label = { Text("Search") },
                            modifier = Modifier.fillMaxWidth(0.8f)
                        )
                    } else {
                        IconButton(onClick = { showSearch = true }) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search"
                            )
                        }
                    }
                    IconButton(onClick = { onNavigateToTimeline() }) {
                        Icon(
                            imageVector = Icons.Default.Timeline,
                            contentDescription = "Timeline"
                        )
                    }
                    MoreOptions(
                        themeViewModel = themeViewModel, 
                        backupViewModel = backupViewModel, 
                        events = events,
                        onSortOptionSelected = { sortOption = it }
                    )
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onAddEvent() },
                containerColor = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Event",
                    tint = Color.White
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Stats Overview
            EventStatsSection(todayEvents = todayEvents.size, totalEvents = events.size)

            Spacer(modifier = Modifier.height(16.dp))

            // Today's Events Section
            if (todayEvents.isNotEmpty()) {
                Text(
                    text = "Today's Events",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(8.dp))

                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp)
                ) {
                    items(todayEvents) { event ->
                        EventCard(
                            event = event,
                            onEventClick = { onEventClick(event) },
                            isToday = true
                        )
                    }
                }
            }

            // All Upcoming Events
            Text(
                text = "Upcoming Events",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(horizontal = 16.dp),
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp)
            ) {
                items(upcomingEvents) { event ->
                    EventCard(
                        event = event,
                        onEventClick = { onEventClick(event) },
                        isToday = false
                    )
                }

                item {
                    if (upcomingEvents.isEmpty()) {
                        EmptyState()
                    }
                }
            }
        }
    }
}

@Composable
fun EventStatsSection(todayEvents: Int, totalEvents: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            StatItem(
                count = todayEvents.toString(),
                label = "Today",
                icon = Icons.Default.CalendarToday,
                color = MaterialTheme.colorScheme.primary
            )
            StatItem(
                count = totalEvents.toString(),
                label = "Total",
                icon = Icons.Default.Event,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

@Composable
fun StatItem(count: String, label: String, icon: androidx.compose.ui.graphics.vector.ImageVector, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .background(color.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = color,
                modifier = Modifier.size(30.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = count,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = color
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun EventCard(event: Event, onEventClick: () -> Unit, isToday: Boolean) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale = if (isPressed) 0.96f else 1f
    val elevation = if (isPressed) 8.dp else 4.dp

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .graphicsLayer { this.scaleX = scale; this.scaleY = scale }
            .clickable(
                interactionSource = interactionSource,
                indication = rememberRipple(),
                onClick = onEventClick
            ),
        colors = CardDefaults.cardColors(
            containerColor = if (isToday) {
                MaterialTheme.colorScheme.primaryContainer
            } else {
                MaterialTheme.colorScheme.surface
            }
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = elevation),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Event Icon
            Icon(
                imageVector = Icons.Default.Event,
                contentDescription = "Event",
                tint = if (isToday) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.secondary,
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Event Details
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = event.title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurface
                )

                if (!event.description.isNullOrBlank()) {
                    Text(
                        text = event.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }

                Text(
                    text = formatEventDateTime(event.date, event.reminderTimes.firstOrNull()),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.outline,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            // Days remaining badge for future events
            if (!isToday) {
                val daysRemaining = calculateDaysRemaining(event.date)
                if (daysRemaining > 0) {
                    Box(
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colorScheme.secondaryContainer,
                                shape = CircleShape
                            )
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "${daysRemaining}d",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun EmptyState() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.Event,
            contentDescription = "No Events",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(64.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "No events yet",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Add a new event to get started",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

private fun calculateDaysRemaining(date: LocalDate): Long {
    return ChronoUnit.DAYS.between(LocalDate.now(), date)
}

private fun formatEventDateTime(date: LocalDate, time: LocalTime?): String {
    val datePattern = "MMM dd, yyyy"
    val timePattern = "HH:mm a"
    val dateStr = date.format(DateTimeFormatter.ofPattern(datePattern))
    val timeStr = time?.format(DateTimeFormatter.ofPattern(timePattern)) ?: ""
    return "$dateStr $timeStr".trim()
}

@Composable
fun MoreOptions(
    themeViewModel: ThemeViewModel,
    backupViewModel: BackupViewModel,
    events: List<Event>,
    onSortOptionSelected: (SortOption) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        IconButton(onClick = { expanded = true }) {
            Icon(Icons.Default.MoreVert, contentDescription = "More options")
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            Theme.values().forEach { theme ->
                DropdownMenuItem(onClick = {
                    themeViewModel.setTheme(theme)
                    expanded = false
                }, text = { Text(text = theme.name) })
            }
            DropdownMenuItem(onClick = {
                backupViewModel.exportEvents(events)
                expanded = false
            }, text = { Text(text = "Export") })
            DropdownMenuItem(onClick = {
                backupViewModel.importEvents()
                expanded = false
            }, text = { Text(text = "Import") })
            SortOption.values().forEach { sortOption ->
                DropdownMenuItem(onClick = {
                    onSortOptionSelected(sortOption)
                    expanded = false
                }, text = { Text(text = "Sort by ${sortOption.name}") })
            }
        }
    }
}
