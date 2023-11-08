package io.github.aptemkov.pexelsapp.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun HomeScreen(navController: NavController) {
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