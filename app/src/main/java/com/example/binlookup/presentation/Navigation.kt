package com.example.binlookup.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.binlookup.presentation.history.HistoryScreen
import com.example.binlookup.presentation.lookup.BinLookupScreen

@Composable
fun BinLookupNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.BinLookup.route
    ) {
        composable(route = Screen.BinLookup.route) {
            BinLookupScreen(
                onNavigateToHistory = {
                    navController.navigate(Screen.History.route)
                }
            )
        }
        
        composable(route = Screen.History.route) {
            HistoryScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}

sealed class Screen(val route: String) {
    object BinLookup : Screen("bin_lookup")
    object History : Screen("history")
}