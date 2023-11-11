package io.github.aptemkov.pexelsapp.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import io.github.aptemkov.pexelsapp.app.presentation.AnimatedSplashScreen
import io.github.aptemkov.pexelsapp.app.presentation.BookmarksScreen
import io.github.aptemkov.pexelsapp.app.presentation.details.DetailsScreen
import io.github.aptemkov.pexelsapp.app.presentation.home.HomeScreen

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    startDestination: String
) {
    NavHost(
        navController,
        startDestination = startDestination
    ) {
        composable(Screen.Splash.route) { AnimatedSplashScreen(navController) }
        composable(Screen.Home.route) { HomeScreen(navController) }
        composable(Screen.Bookmarks.route) { BookmarksScreen() }
        composable(Screen.Details.route) { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            DetailsScreen(arguments.getString("id"))
        }
    }
}