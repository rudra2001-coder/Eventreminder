package com.rudra.eventreminder.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Upcoming
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    // Bottom Nav Screens
    object Dashboard : Screen("dashboard", "Home", Icons.Default.Dashboard)
    object UpcomingEvents : Screen("upcoming", "Upcoming", Icons.Default.Upcoming)
    object PastEvents : Screen("past", "Past", Icons.Default.History)
    object Memories : Screen("memories", "Memories", Icons.Default.Memory)
    object Calendar : Screen("calendar", "Calendar", Icons.Default.CalendarToday)
    object Menu : Screen("menu", "Menu", Icons.Default.Menu)
    object Settings : Screen("settings", "Settings", Icons.Default.Settings)


    // Other Screens
    object AddEditEvent : Screen("add_edit_event/{eventId}", "Add/Edit Event", Icons.Default.Add) {
        fun createRoute(eventId: Long) = "add_edit_event/$eventId"
    }
    object EventDetail : Screen("event_detail/{eventId}", "Event Detail", Icons.Default.Event) {
        fun createRoute(eventId: Long) = "event_detail/$eventId"
    }
}