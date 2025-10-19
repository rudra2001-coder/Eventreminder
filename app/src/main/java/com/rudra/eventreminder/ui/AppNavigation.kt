package com.rudra.eventreminder.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rudra.eventreminder.viewmodel.EventViewModel
import com.rudra.eventreminder.ui.screens.HomeScreen
import com.rudra.eventreminder.ui.screens.AddMemoryScreen
import com.rudra.eventreminder.ui.screens.EmojiExplorerScreen
import com.rudra.eventreminder.ui.screens.UpcomingEventsScreen
import com.rudra.eventreminder.ui.screens.PastEventsScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val viewModel: EventViewModel = viewModel()

    NavHost(navController = navController, startDestination = AppScreen.Home.route) {
        composable(AppScreen.Home.route) {
            HomeScreen(
                viewModel = viewModel,
                onNavigateToAddMemory = { navController.navigate(AppScreen.AddMemory.route) },
                onNavigateToEmojiExplorer = { navController.navigate(AppScreen.EmojiExplorer.route) },
                onNavigateToUpcomingEvents = { navController.navigate(AppScreen.UpcomingEvents.route) },
                onNavigateToPastEvents = { navController.navigate(AppScreen.PastEvents.route) }
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
    }
}
