package io.github.aptemkov.pexelsapp.presentation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Home : Screen("home")
    object Bookmarks : Screen("bookmarks")
    object Details : Screen("details/{id}")
}