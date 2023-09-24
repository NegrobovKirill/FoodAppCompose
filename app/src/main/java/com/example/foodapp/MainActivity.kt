package com.example.foodapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.foodapp.ui.presentation.screens.MainScreen
import com.example.foodapp.ui.presentation.screens.SplashScreen
import com.example.foodapp.ui.theme.FoodAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

                // A surface container using the 'background' color from the theme
            navigation()

        }
    }
}

@Composable
fun navigation(){

    val navController = rememberNavController()
    NavHost(navController = navController,
        startDestination = "splash_screen")
    {
        composable("splash_screen"){
            SplashScreen(navController = navController)
        }
        composable("main_screen"){
            MainScreen()
        }
    }
}

@Preview()
@Composable
fun GreetingPreview() {
    FoodAppTheme {

    }
}

