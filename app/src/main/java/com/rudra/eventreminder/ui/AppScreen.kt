package com.rudra.eventreminder.ui

sealed class AppScreen(val route: String) {
    object Home : AppScreen("home")
    object AddMemory : AppScreen("add_memory")
    object EmojiExplorer : AppScreen("emoji_explorer")
    object UpcomingEvents : AppScreen("upcoming_events")
    object PastEvents : AppScreen("past_events")
    object Timeline : AppScreen("timeline")
    object Countdown : AppScreen("countdown/{eventId}") {
        fun createRoute(eventId: Int) = "countdown/$eventId"
    }
    object BirthdayPack : AppScreen("birthday_pack/{eventId}") {
        fun createRoute(eventId: Int) = "birthday_pack/$eventId"
    }
    object AnniversaryPack : AppScreen("anniversary_pack/{eventId}") {
        fun createRoute(eventId: Int) = "anniversary_pack/$eventId"
    }
    object TravelPack : AppScreen("travel_pack/{eventId}") {
        fun createRoute(eventId: Int) = "travel_pack/$eventId"
    }
    object WorkPack : AppScreen("work_pack/{eventId}") {
        fun createRoute(eventId: Int) = "work_pack/$eventId"
    }
}
