package com.rudra.eventreminder.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController

@Composable
fun RememberNavigationActions(navController: NavHostController): NavigationActions {
    return remember(navController) {
        NavigationActions(navController)
    }
}