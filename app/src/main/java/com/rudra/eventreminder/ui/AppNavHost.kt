package com.rudra.eventreminder.ui



import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.rudra.eventreminder.viewmodel.EventViewModel

@Composable
fun AppNavHost(viewModel: EventViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "dashboard") {
        composable("dashboard") {
            DashboardScreen(viewModel = viewModel, onAddClick = { navController.navigate("add") })
        }
        composable("add") {
            AddEditEventScreen(viewModel = viewModel, onDone = { navController.popBackStack() })
        }
    }
}
