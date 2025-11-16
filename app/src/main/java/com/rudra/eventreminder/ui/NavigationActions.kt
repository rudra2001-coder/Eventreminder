package com.rudra.eventreminder.ui

import androidx.navigation.NavHostController
import com.rudra.eventreminder.ui.screens.Screen

class NavigationActions(private val navController: NavHostController) {
    fun navigateToDashboard() = navController.navigate(Screen.Dashboard.route)
    
    fun navigateToEventDetail(eventId: Long) = 
        navController.navigate(Screen.EventDetail.createRoute(eventId))
    
    fun navigateToAddEditEvent(eventId: Long = -1L) = 
        navController.navigate(Screen.AddEditEvent.createRoute(eventId))
    
    fun navigateToMemories() = navController.navigate(Screen.Memories.route)
    
    fun navigateToCalendar() = navController.navigate(Screen.Calendar.route)

    fun navigateToSettings() = navController.navigate(Screen.Settings.route)
    
    fun navigateBack() = navController.popBackStack()
}