package com.example.foodapp.ui.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
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
import kotlinx.coroutines.launch


@Composable
fun ShoppingScreen(viewModel: MainViewModel, navController: NavController){

    val basketList = viewModel.basketList.observeAsState(listOf())
    val price = viewModel.price.observeAsState()


    Box() {

        Column {

            AppBar(navController)
            if (price.value != 0) {
                Column {

                    LazyColumn(modifier = Modifier.padding(bottom = 72.dp)) {
                        items(basketList.value) { model ->
                            if (model.add > 0) {
                                BasketItem(model,
                                    onAddPlusButtonClickListener = { viewModel.changeAddPlus(it) },
                                    onAddMinusButtonClickListener = { viewModel.changeAddMinus(it) }
                                )
                            }
                        }
                    }
                }

            } else {
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Пусто, выберите блюда\n" + "в каталоге :)",
                            fontSize = 16.sp,
                            modifier = Modifier.alpha(0.5f),
                            textAlign = TextAlign.Center
                        )
                    }

                }
            }
        }
        if (price.value != 0){
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row {
            }
            Button(modifier = Modifier
                .height(72.dp)
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
                colors = ButtonDefaults.buttonColors(Orange),
                shape = RoundedCornerShape(8.dp),
                onClick = {}

            ) {
                Text(
                    text = "Заказать за " + (price.value?.div(100)).toString() + " ₽",
                    color = Color.White,
                    fontSize = 16.sp
                )
            }
        }
        }


    }

}

@Composable
fun BasketItem(
    productItemModel: ProductItemModel,
    onAddPlusButtonClickListener: (ProductItemModel) -> Unit,
    onAddMinusButtonClickListener: (ProductItemModel) -> Unit
) {

    Row(modifier = Modifier
        .height(130.dp)
        .fillMaxWidth()
        .background(Color.White)
        .padding(16.dp))
    {
        Image(painter = painterResource(id = R.drawable.previe), contentDescription = null)
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.height(98.dp)) {
            Text(text = productItemModel.productItem.name,
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.roboto_regular))
            )
            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier
                    .height(44.dp)
                    .width(135.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                Button(
                    modifier = Modifier.size(44.dp),
                    colors = ButtonDefaults.buttonColors(GrayBg),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(10.dp),
                    onClick = { onAddMinusButtonClickListener(productItemModel) }) {
                    Icon(
                        painter = painterResource(id = R.drawable.minus),
                        contentDescription = null,
                        tint = Orange
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    modifier = Modifier.width(30.dp),
                    text = productItemModel.add.toString(),
                    fontFamily = FontFamily(Font(R.font.roboto_medium)),
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    modifier = Modifier.size(44.dp),
                    colors = ButtonDefaults.buttonColors(GrayBg),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(10.dp),
                    onClick = { onAddPlusButtonClickListener(productItemModel) }) {
                    Icon(
                        painter = painterResource(id = R.drawable.plus),
                        contentDescription = null,
                        tint = Orange
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(verticalArrangement = Arrangement.SpaceBetween) {
                    Text(text = (productItemModel.productItem.price_current/100).toString() + " ₽",
                        fontFamily = FontFamily(Font(R.font.roboto_medium)),
                        fontSize = 16.sp,
                        color = Dark,
                        textAlign = TextAlign.Right
                    )
                    if (productItemModel.productItem.price_old != null){
                        Text(text = (productItemModel.productItem.price_old/100).toString() + " ₽",
                            fontFamily = FontFamily(Font(R.font.roboto_regular)),
                            textDecoration = TextDecoration.LineThrough,
                            fontSize = 14.sp,
                            color = Dark,
                            modifier = Modifier.alpha(0.5f),
                            textAlign = TextAlign.Right
                        )
                    }
                }
            }
        }
    }
    Divider()
}

@Composable
fun AppBar(navController: NavController){
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(Color.White)
            .height(56.dp)
            .padding(16.dp)) {
        IconButton(modifier = Modifier.size(24.dp),
            onClick = {
                navController.popBackStack()
//            navController.navigate("main_screen"){
//            popUpTo(0) }
        }
        ) {
            Icon( painter = painterResource(id = R.drawable.back),
                contentDescription = null, tint = Orange)
        }
        Spacer(modifier = Modifier.width(32.dp))
        Text(text = "Корзина", fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,color = Dark)
    }
}


