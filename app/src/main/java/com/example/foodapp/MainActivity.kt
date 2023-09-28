package com.example.foodapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.foodapp.ui.presentation.screens.MainScreen
import com.example.foodapp.ui.presentation.screens.SplashScreen
import com.example.foodapp.ui.presentation.viewmodels.MainViewModel
import java.io.InputStream

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var jsonProducts : String? = null
            try {
                val inputStream: InputStream = applicationContext.assets.open("Products.json")
                jsonProducts = inputStream.bufferedReader().use { it.readText() }

            }catch (e: Exception){

            }

        var jsonCategories : String? = null
        try {
            val inputStream: InputStream = applicationContext.assets.open("Categories.json")
            jsonCategories = inputStream.bufferedReader().use { it.readText() }

        }catch (e: Exception){

        }

        var jsonTags : String? = null
        try {
            val inputStream: InputStream = applicationContext.assets.open("Tags.json")
            jsonTags = inputStream.bufferedReader().use { it.readText() }

        }catch (e: Exception){

        }



        setContent {

            val viewModel: MainViewModel = viewModel()
            viewModel.update(jsonProducts,jsonCategories,jsonTags)
            viewModel.firstInit()


            navigation(viewModel)

        }
    }
}

@Composable
fun navigation(viewModel: MainViewModel){

    val navController = rememberNavController()
    NavHost(navController = navController,
        startDestination = "splash_screen")
    {
        composable("splash_screen"){
            SplashScreen(navController = navController)
        }
        composable("main_screen"){


            MainScreen(viewModel)
        }
    }
}



