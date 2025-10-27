package com.rudra.eventreminder.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.rudra.eventreminder.viewmodel.BackupViewModel
import com.rudra.eventreminder.viewmodel.CountdownViewModel
import com.rudra.eventreminder.viewmodel.EventViewModel
import com.rudra.eventreminder.ui.screens.AddMemoryScreen
import com.rudra.eventreminder.ui.screens.AnniversaryPackScreen
import com.rudra.eventreminder.ui.screens.BirthdayPackScreen
import com.rudra.eventreminder.ui.screens.CountdownScreen
import com.rudra.eventreminder.ui.screens.EmojiExplorerScreen
import com.rudra.eventreminder.ui.screens.PastEventsScreen
import com.rudra.eventreminder.ui.screens.TimelineScreen
import com.rudra.eventreminder.ui.screens.TravelPackScreen
import com.rudra.eventreminder.ui.screens.UpcomingEventsScreen
import com.rudra.eventreminder.ui.screens.WorkPackScreen
import com.rudra.eventreminder.viewmodel.ThemeViewModel

@Composable
fun AppNavigation(themeViewModel: ThemeViewModel, backupViewModel: BackupViewModel) {
    val navController = rememberNavController()
    val viewModel: EventViewModel = viewModel()
    val countdownViewModel: CountdownViewModel = viewModel()

    NavHost(navController = navController, startDestination = AppScreen.Home.route) {
        composable(AppScreen.Home.route) {
            DashboardScreen(
                viewModel = viewModel,
                themeViewModel = themeViewModel,
                backupViewModel = backupViewModel,
                onEventClick = { event ->
                    when (event.eventType) {
                        "Birthday" -> navController.navigate(AppScreen.BirthdayPack.createRoute(event.id))
                        "Anniversary" -> navController.navigate(AppScreen.AnniversaryPack.createRoute(event.id))
                        "Travel" -> navController.navigate(AppScreen.TravelPack.createRoute(event.id))
                        "Work" -> navController.navigate(AppScreen.WorkPack.createRoute(event.id))
                        else -> navController.navigate(AppScreen.Countdown.createRoute(event.id))
                    }
                },
                onAddEvent = { navController.navigate(AppScreen.AddMemory.route) },
                onNavigateToTimeline = { navController.navigate(AppScreen.Timeline.route) }
            )
        }
        composable(AppScreen.AddMemory.route) {
            AddMemoryScreen(viewModel = viewModel, onDone = { navController.popBackStack() })
        }
        composable(AppScreen.EmojiExplorer.route) {
            EmojiExplorerScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(AppScreen.UpcomingEvents.route) {
            UpcomingEventsScreen(viewModel = viewModel, onNavigateBack = { navController.popBackStack() })
        }
        composable(AppScreen.PastEvents.route) {
            PastEventsScreen(viewModel = viewModel, onNavigateBack = { navController.popBackStack() })
        }
        composable(AppScreen.Timeline.route) {
            TimelineScreen(viewModel = viewModel, onNavigateBack = { navController.popBackStack() })
        }
        composable(
            route = AppScreen.Countdown.route,
            arguments = listOf(navArgument("eventId") { type = NavType.IntType })
        ) {
            val eventId = it.arguments?.getInt("eventId")
            if (eventId != null) {
                CountdownScreen(viewModel = countdownViewModel, eventId = eventId, eventViewModel = viewModel)
            }
        }
        composable(
            route = AppScreen.BirthdayPack.route,
            arguments = listOf(navArgument("eventId") { type = NavType.IntType })
        ) {
            val eventId = it.arguments?.getInt("eventId")
            if (eventId != null) {
                val event by viewModel.getEvent(eventId.toLong()).collectAsState(initial = null)
                event?.let { BirthdayPackScreen(event = it) }
            }
        }
        composable(
            route = AppScreen.AnniversaryPack.route,
            arguments = listOf(navArgument("eventId") { type = NavType.IntType })
        ) {
            val eventId = it.arguments?.getInt("eventId")
            if (eventId != null) {
                val event by viewModel.getEvent(eventId.toLong()).collectAsState(initial = null)
                event?.let { AnniversaryPackScreen(event = it) }
            }
        }
        composable(
            route = AppScreen.TravelPack.route,
            arguments = listOf(navArgument("eventId") { type = NavType.IntType })
        ) {
            val eventId = it.arguments?.getInt("eventId")
            if (eventId != null) {
                val event by viewModel.getEvent(eventId.toLong()).collectAsState(initial = null)
                event?.let { TravelPackScreen(event = it) }
            }
        }
        composable(
            route = AppScreen.WorkPack.route,
            arguments = listOf(navArgument("eventId") { type = NavType.IntType })
        ) {
            val eventId = it.arguments?.getInt("eventId")
            if (eventId != null) {
                val event by viewModel.getEvent(eventId.toLong()).collectAsState(initial = null)
                event?.let { WorkPackScreen(event = it) }
            }
        }
    }
}
