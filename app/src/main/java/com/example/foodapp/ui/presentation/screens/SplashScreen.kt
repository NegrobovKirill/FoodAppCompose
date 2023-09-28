package com.example.foodapp.ui.presentation.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.navigation.NavController
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.airbnb.lottie.compose.rememberLottieDynamicProperties
import com.airbnb.lottie.compose.rememberLottieDynamicProperty
import com.example.foodapp.R
import com.example.foodapp.ui.theme.Orange
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController){

    LaunchedEffect(key1 = true){
        delay(2500)
        navController.navigate("main_screen"){
            popUpTo(0)
        }
    }

    val dynamicProperties = rememberLottieDynamicProperties(
        rememberLottieDynamicProperty(property = LottieProperty.COLOR, keyPath = arrayOf("Mouth", "Group 1","Fill 1"), value = Color.White.toArgb()),
        rememberLottieDynamicProperty(property = LottieProperty.COLOR, keyPath = arrayOf("Big eye", "Group 1","Fill 1"), value = Color.White.toArgb()),
        rememberLottieDynamicProperty(property = LottieProperty.COLOR, keyPath = arrayOf("Smol eye", "Group 1","Fill 1"), value = Color.White.toArgb()),
        rememberLottieDynamicProperty(property = LottieProperty.COLOR, keyPath = arrayOf("dies", "Group 1","Fill 1"), value = Color.White.toArgb()),
        rememberLottieDynamicProperty(property = LottieProperty.COLOR, keyPath = arrayOf("dies", "Group 2","Fill 1"), value = Color.White.toArgb()),
        rememberLottieDynamicProperty(property = LottieProperty.COLOR, keyPath = arrayOf("f", "Group 1","Fill 1"), value = Color.White.toArgb()),
        rememberLottieDynamicProperty(property = LottieProperty.COLOR, keyPath = arrayOf("f 2", "Group 1","Fill 1"), value = Color.White.toArgb()),
        )

    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.splash_screen_animation))

    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = Orange),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {

        LottieAnimation(composition = composition,dynamicProperties = dynamicProperties)
        

        
    }
}
