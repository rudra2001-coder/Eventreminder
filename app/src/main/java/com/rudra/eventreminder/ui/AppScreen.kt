package com.rudra.eventreminder.ui

sealed class AppScreen(val route: String) {
    object Home : AppScreen("home")
    object AddMemory : AppScreen("add_memory")
    object EmojiExplorer : AppScreen("emoji_explorer")
    object UpcomingEvents : AppScreen("upcoming_events")
    object PastEvents : AppScreen("past_events")
}