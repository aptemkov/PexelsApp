package io.github.aptemkov.pexelsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Button
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.navigation.compose.NavHost
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import io.github.aptemkov.pexelsapp.ui.theme.PexelsAppTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
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

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp() {
    val navController = rememberNavController()
    val items = listOf(Screen.Main, Screen.Second)
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute in items.map { it.route }) {
                BottomNavigation {
                    items.forEach { screen ->
                        BottomNavigationItem(
                            icon = {
                                Icon(Icons.Filled.Home, contentDescription = null)
                            },
                            label = { Text(screen.route) },
                            selected = currentRoute == screen.route,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.startDestinationId)
                                    launchSingleTop = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = Screen.Splash.route,
            Modifier.padding(innerPadding)
        ) {
            composable(Screen.Splash.route) { SplashScreen(navController) }
            composable(Screen.Main.route) { MainScreen(navController) }
            composable(Screen.Second.route) { SecondScreen() }
            composable(Screen.Details.route) { backStackEntry ->
                val arguments = requireNotNull(backStackEntry.arguments)
                DetailsScreen(arguments.getString("id"))
            }
        }
    }
}


@Composable
fun SplashScreen(navController: NavController) {
    LaunchedEffect(key1 = true) {
        delay(2000L)
        navController.navigate(Screen.Main.route) {
            popUpTo(Screen.Splash.route) { inclusive = true }
        }
    }
    Text(text = "splash")
    // Здесь ваш код для SplashScreen
    // После завершения SplashScreen, используйте следующий код для перехода к MainScreen
    //navController.navigate(Screen.Main.route)
}

@Composable
fun MainScreen(navController: NavController) {
    // Здесь ваш код для MainScreen
    // Используйте следующий код для перехода к DetailsScreen

    Column {

        Text(text = "Main")
        Button(onClick = {
            navController.navigate("details/testFromMain")
        }) {
            Text(text = "details")
        }

    }

}

@Composable
fun SecondScreen() {
    Text(text = "Second")
    // Здесь ваш код для SecondScreen
}

@Composable
fun DetailsScreen(id: String?) {
    Text(text = "details: $id")
    // Здесь ваш код для DetailsScreen
}

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Main : Screen("main")
    object Second : Screen("second")
    object Details : Screen("details/{id}")
}