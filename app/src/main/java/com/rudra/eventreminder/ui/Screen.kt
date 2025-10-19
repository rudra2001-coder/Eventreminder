package com.rudra.eventreminder.ui

sealed class Screen(val route: String) {
    object Dashboard : Screen("dashboard")
    object AddEditEvent : Screen("add_edit_event")
    object EventDetail : Screen("event_detail/{eventId}") {
        fun createRoute(eventId: Long) = "event_detail/$eventId"
    }
}