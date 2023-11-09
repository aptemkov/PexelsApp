package io.github.aptemkov.pexelsapp

import android.annotation.SuppressLint
import android.content.res.Resources
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import io.github.aptemkov.pexelsapp.navigation.BottomBar
import io.github.aptemkov.pexelsapp.presentation.AnimatedSplashScreen
import io.github.aptemkov.pexelsapp.presentation.BookmarksScreen
import io.github.aptemkov.pexelsapp.presentation.DetailsScreen
import io.github.aptemkov.pexelsapp.presentation.HomeScreen
import io.github.aptemkov.pexelsapp.navigation.Screen
import io.github.aptemkov.pexelsapp.navigation.SetupNavGraph
import io.github.aptemkov.pexelsapp.ui.theme.PexelsAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PexelsAppTheme {
                MyApp()
            }
            /*
            PexelsAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
             */
        }
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp() {
    val navController = rememberNavController()
    val items = listOf(Screen.Home, Screen.Bookmarks)
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute in items.map { it.route }) {
                BottomBar(navController = navController)
                //BottomNavigation(backgroundColor = MaterialTheme.colorScheme.background) {
                  //  items.forEach { screen ->

/*                        BottomNavigationItem(
                            icon = {
                                Icon(Icons.Filled.Home, contentDescription = null)
                            },
//                            label = { Text(screen.route) },
                            selected = currentRoute == screen.route,
//                            selectedContentColor = MaterialTheme.colorScheme.tertiary,
//                            unselectedContentColor = MaterialTheme.colorScheme.primary,

                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.startDestinationId)
                                    launchSingleTop = true
                                }
                            }
                        )*/

                //}
            }
        }
    ) {
        SetupNavGraph(
            navController = navController,
            startDestination = Screen.Splash.route
        )

    }
}

