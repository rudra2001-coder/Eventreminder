package com.rudra.eventreminder.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.rudra.eventreminder.viewmodel.EventViewModel
import com.rudra.eventreminder.viewmodel.EventViewModelFactory

@Composable
fun AppNavHost(navController: NavHostController = rememberNavController()) {
    val viewModel: EventViewModel = viewModel(factory = EventViewModelFactory(navController.context.applicationContext as android.app.Application))
    NavHost(navController = navController, startDestination = Screen.Dashboard.route) {
        composable(Screen.Dashboard.route) {
            DashboardScreen(viewModel = viewModel, onEventClick = { eventId ->
                navController.navigate(Screen.EventDetail.createRoute(eventId))
            }, onAddEvent = {
                navController.navigate("add_edit_event/-1")
            })
        }
        composable(
            route = "add_edit_event/{eventId}",
            arguments = listOf(navArgument("eventId") { type = NavType.LongType })
        ) { backStackEntry ->
            val eventId = backStackEntry.arguments?.getLong("eventId") ?: -1L
            AddEditEventScreen(viewModel = viewModel, onDone = { navController.popBackStack() }, eventId = eventId)
        }
        composable(Screen.EventDetail.route) { backStackEntry ->
            val eventId = backStackEntry.arguments?.getString("eventId")?.toLongOrNull()
            eventId?.let { id ->
                EventDetailScreen(viewModel = viewModel, eventId = id, onEdit = { eventIdToEdit ->
                    navController.navigate("add_edit_event/$eventIdToEdit")
                })
            }
        }
    }
}
