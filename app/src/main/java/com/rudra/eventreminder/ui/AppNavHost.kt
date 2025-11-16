package com.rudra.eventreminder.ui

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.rudra.eventreminder.ui.screens.AddEditEventScreen
import com.rudra.eventreminder.ui.screens.AddMemoryScreen
import com.rudra.eventreminder.ui.screens.CalendarScreen
import com.rudra.eventreminder.ui.screens.DashboardScreen
import com.rudra.eventreminder.ui.screens.EventDetailScreen
import com.rudra.eventreminder.ui.screens.MenuScreen
import com.rudra.eventreminder.ui.screens.PastEventsScreen
import com.rudra.eventreminder.ui.screens.Screen
import com.rudra.eventreminder.ui.screens.SettingsScreen
import com.rudra.eventreminder.ui.screens.UpcomingEventsScreen
import com.rudra.eventreminder.viewmodel.BackupViewModel
import com.rudra.eventreminder.viewmodel.EventViewModel
import com.rudra.eventreminder.viewmodel.EventViewModelFactory
import com.rudra.eventreminder.viewmodel.ThemeViewModel

private const val ANIMATION_DURATION = 300

private fun enterTransition(initialRoute: String?): EnterTransition {
    return when (initialRoute) {
        Screen.UpcomingEvents.route ->
            slideInHorizontally(initialOffsetX = { 300 }, animationSpec = tween(ANIMATION_DURATION)) + fadeIn(animationSpec = tween(ANIMATION_DURATION))
        Screen.PastEvents.route ->
            slideInHorizontally(initialOffsetX = { -300 }, animationSpec = tween(ANIMATION_DURATION)) + fadeIn(animationSpec = tween(ANIMATION_DURATION))
        else ->
            slideInHorizontally(initialOffsetX = { 300 }, animationSpec = tween(ANIMATION_DURATION)) + fadeIn(animationSpec = tween(ANIMATION_DURATION))
    }
}

private fun exitTransition(targetRoute: String?): ExitTransition {
    return when (targetRoute) {
        Screen.Dashboard.route ->
            slideOutHorizontally(targetOffsetX = { -300 }, animationSpec = tween(ANIMATION_DURATION)) + fadeOut(animationSpec = tween(ANIMATION_DURATION))
        else ->
            slideOutHorizontally(targetOffsetX = { 300 }, animationSpec = tween(ANIMATION_DURATION)) + fadeOut(animationSpec = tween(ANIMATION_DURATION))
    }
}

private fun popEnterTransition(initialRoute: String?): EnterTransition {
    return when (initialRoute) {
        Screen.Dashboard.route ->
            slideInHorizontally(initialOffsetX = { -300 }, animationSpec = tween(ANIMATION_DURATION)) + fadeIn(animationSpec = tween(ANIMATION_DURATION))
        else ->
            slideInHorizontally(initialOffsetX = { 300 }, animationSpec = tween(ANIMATION_DURATION)) + fadeIn(animationSpec = tween(ANIMATION_DURATION))
    }
}

private fun popExitTransition(targetRoute: String?): ExitTransition {
    return when (targetRoute) {
        Screen.Dashboard.route ->
            slideOutHorizontally(targetOffsetX = { 300 }, animationSpec = tween(ANIMATION_DURATION)) + fadeOut(animationSpec = tween(ANIMATION_DURATION))
        else ->
            slideOutHorizontally(targetOffsetX = { -300 }, animationSpec = tween(ANIMATION_DURATION)) + fadeOut(animationSpec = tween(ANIMATION_DURATION))
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.Dashboard.route, // Make configurable
    themeViewModel: ThemeViewModel,
    backupViewModel: BackupViewModel,
) {
    val viewModel: EventViewModel = viewModel(
        factory = EventViewModelFactory(
            navController.context.applicationContext as android.app.Application
        )
    )
    val navigationActions = remember(navController) { NavigationActions(navController) }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = startDestination, // Use parameter
            modifier = Modifier.padding(paddingValues),
            enterTransition = { enterTransition(initialState.destination.route) },
            exitTransition = { exitTransition(targetState.destination.route) },
            popEnterTransition = { popEnterTransition(initialState.destination.route) },
            popExitTransition = { popExitTransition(targetState.destination.route) }
        ) {
            // Dashboard Screen
            composable(Screen.Dashboard.route) {
                DashboardScreen(
                    viewModel = viewModel,
                    themeViewModel = themeViewModel,
                    backupViewModel = backupViewModel,
                    onEventClick = { event ->
                        navigationActions.navigateToEventDetail(event.id.toLong())
                    },
                    onAddEvent = {
                        navigationActions.navigateToAddEditEvent(-1L)
                    },
                    onNavigateToTimeline = {
                        // TODO: Implement Timeline navigation
                    },
                    onNavigateToGiftReminder = { _ ->
                        // TODO: Implement Gift Reminder navigation
                    },
                    onNavigateToNotes = { _ ->
                        // TODO: Implement Notes navigation
                    },
                    onNavigateToHabits = {
                        // TODO: Implement Habits navigation
                    },
                    onNavigateToMemoryBox = {
                        navigationActions.navigateToMemories()
                    },
                    onNavigateToCalendar = {
                        navigationActions.navigateToCalendar()
                    }
                )
            }

            // Upcoming Events Screen
            composable(Screen.UpcomingEvents.route) {
                UpcomingEventsScreen(
                    viewModel = viewModel,
                    onEventClick = { event ->
                        navigationActions.navigateToEventDetail(event.id.toLong())
                    },
                    onNavigateBack = { navigationActions.navigateBack() }
                )
            }

            // Past Events Screen
            composable(Screen.PastEvents.route) {
                PastEventsScreen(
                    viewModel = viewModel,
                    onEventClick = { event ->
                        navigationActions.navigateToEventDetail(event.id.toLong())
                    },
                    onNavigateBack = { navigationActions.navigateBack() }
                )
            }

            // Memories Screen
            composable(Screen.Memories.route) {
                AddMemoryScreen(
                    onBack = { navigationActions.navigateBack() },
                    viewModel = viewModel
                )
            }

            // Calendar Screen
            composable(Screen.Calendar.route) {
                CalendarScreen(viewModel = viewModel)
            }

            // Menu Screen
            composable(Screen.Menu.route) {
                MenuScreen()
            }

            // Settings Screen
            composable(Screen.Settings.route) {
                SettingsScreen()
            }

            // Add/Edit Event Screen
            composable(
                route = Screen.AddEditEvent.route,
                arguments = listOf(navArgument("eventId") { type = NavType.LongType })
            ) { backStackEntry ->
                val eventId = backStackEntry.arguments?.getLong("eventId") ?: -1L
                AddEditEventScreen(
                    viewModel = viewModel,
                    onDone = { navigationActions.navigateBack() },
                    eventId = eventId
                )
            }

            // Event Detail Screen
            composable(
                route = Screen.EventDetail.route,
                arguments = listOf(navArgument("eventId") { type = NavType.LongType })
            ) { backStackEntry ->
                val eventId = backStackEntry.arguments?.getLong("eventId") ?: -1L
                if (eventId == -1L) {
                    // Handle invalid event ID - maybe show error or navigate back
                    LaunchedEffect(Unit) {
                        navigationActions.navigateBack()
                    }
                    return@composable
                }
                EventDetailScreen(
                    viewModel = viewModel,
                    eventId = eventId,
                    onEdit = { eventIdToEdit ->
                        navigationActions.navigateToAddEditEvent(eventIdToEdit)
                    },
                    onDelete = {
                        viewModel.deleteEvent(eventId)
                        navigationActions.navigateBack()
                    },
                    onBack = { navigationActions.navigateBack() }
                )
            }
        }
    }
}
