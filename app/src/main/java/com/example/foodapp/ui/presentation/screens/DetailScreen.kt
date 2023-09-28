package com.example.foodapp.ui.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.foodapp.R
import com.example.foodapp.data.model.product.ProductItem
import com.example.foodapp.data.model.product.ProductItemModel
import com.example.foodapp.ui.presentation.viewmodels.MainViewModel
import com.example.foodapp.ui.theme.Dark
import com.example.foodapp.ui.theme.GrayBg
import com.example.foodapp.ui.theme.Orange
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.Duration

@Composable
fun DetailScreen(
    viewModel: MainViewModel,
    id: Int?,
    navController: NavController
){
    val models = viewModel.models.observeAsState(listOf())

    DetailContent(models.value[id!!],onAddPlusButtonClickListener = { viewModel.changeAddPlus(models.value[id])}, navController = navController)
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailContent(productItemModel: ProductItemModel,
                  onAddPlusButtonClickListener: (ProductItemModel) -> Unit,navController: NavController) {
    val productItem = productItemModel

    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val scope = rememberCoroutineScope()

    Scaffold() {
    Box(modifier = Modifier.padding(it)) {

        Column(modifier = Modifier.background(Color.White)) {

            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                Image(
                    modifier = Modifier.fillMaxWidth(1f),
                    painter = painterResource(id = R.drawable.previe),
                    contentDescription = null
                )

                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier
                        .padding(start = 16.dp)
                ) {
                    Text(
                        text = productItem.productItem.name,
                        fontSize = 34.sp,
                        fontFamily = FontFamily(Font(R.font.roboto_medium)),
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))


                Text(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp)
                        .alpha(0.5f), text = productItem.productItem.description
                )
                Spacer(modifier = Modifier.height(24.dp))

                Divider()
                RowDescription(
                    title = "Вес",
                    grams = productItem.productItem.measure.toString(),
                    measureUnit = "г"
                )
                Divider()
                RowDescription(
                    title = "Энерг. ценность",
                    grams = productItem.productItem.energy_per_100_grams.toString(),
                    measureUnit = "ккал"
                )
                Divider()
                RowDescription(
                    title = "Белки",
                    grams = productItem.productItem.carbohydrates_per_100_grams.toString(),
                    measureUnit = "г"
                )
                Divider()
                RowDescription(
                    title = "Жиры",
                    grams = productItem.productItem.fats_per_100_grams.toString(),
                    measureUnit = "г"
                )
                Divider()
                RowDescription(
                    title = "Углеводы",
                    grams = productItem.productItem.proteins_per_100_grams.toString(),
                    measureUnit = "г"
                )
                Divider()
                Row(modifier = Modifier.height(72.dp)) {

                }
            }
        }

        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            FloatingActionButton(modifier = Modifier
                .padding(16.dp)
                .size(44.dp),
                containerColor = Color.White,
                shape = CircleShape,
                onClick = {
                    navController.popBackStack()
//                    navController.navigate("main_screen"){
//                    popUpTo(0)
//                    }
                }
            ) {
                Icon(painter = painterResource(id = R.drawable.back), contentDescription = null)
            }


            Row(
                modifier = Modifier
                    .height(144.dp)
            ) {
                Column() {

                    Row(modifier = Modifier.height(72.dp))
                    {
                        SnackbarHost(hostState = snackbarHostState)
                    }


                    Row(
                        modifier = Modifier
                            .background(Color.White)
                            .height(72.dp)

                    ) {
                        ButtonAddItem(
                            productItemModel = productItemModel,
                            onPlusClick = onAddPlusButtonClickListener,
                            price = (productItem.productItem.price_current / 100).toString(),
                            snackbarHostState,
                            scope
                        )

                    }
                }
            }

        }

        }

    }

}

@Composable
fun RowDescription(title: String, grams: String, measureUnit: String) {
    Row(
        modifier = Modifier
            .height(50.dp)
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 13.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(R.font.roboto_regular)),
            color = Dark,
            modifier = Modifier.alpha(0.5f),
            textAlign = TextAlign.Start,

            )
        Text(
            text = grams + " " + measureUnit, modifier = Modifier.fillMaxWidth(1f),
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(R.font.roboto_medium)),
            color = Dark,
            textAlign = TextAlign.Right
        )
    }
}


@Composable
fun ButtonAddItem(
    productItemModel: ProductItemModel,
    onPlusClick: (ProductItemModel) -> Unit,
    price: String,
    snackbarHostState: SnackbarHostState,
    scope: CoroutineScope
) {

    Button(modifier = Modifier
        .height(72.dp)
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 12.dp),
        colors = ButtonDefaults.buttonColors(Orange),
        shape = RoundedCornerShape(8.dp),
        onClick = {
            scope.launch {
                snackbarHostState.showSnackbar(
                    message = "Добавлено в корзину",
                    duration = SnackbarDuration.Short
                )
            }
            onPlusClick(productItemModel)
        }
    ) {
        Text(
            text = "В корзину за " + price + " ₽",
            color = Color.White,
            fontSize = 16.sp
        )
    }
}


