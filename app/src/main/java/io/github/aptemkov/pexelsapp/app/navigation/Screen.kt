package io.github.aptemkov.pexelsapp.app.navigation

import io.github.aptemkov.pexelsapp.R

sealed class Screen(
    val route: String,
    val icon_inactive: Int? = null,
    val icon_active: Int? = null
) {
    object Splash : Screen(route = "splash")
    object Home : Screen(
        route = "home",
        icon_inactive = R.drawable.ic_home_inactive,
        icon_active = R.drawable.ic_home_active
    )

    object Bookmarks : Screen(
        route = "bookmarks",
        icon_inactive = R.drawable.ic_bookmark_inactive,
        icon_active = R.drawable.ic_bookmark_active
    )

    object Details : Screen(route = "details/{id}")
}