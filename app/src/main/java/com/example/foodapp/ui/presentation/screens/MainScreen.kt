package com.example.foodapp.ui.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.foodapp.R
import com.example.foodapp.data.model.product.ProductItem
import com.example.foodapp.data.model.product.ProductItemModel
import com.example.foodapp.ui.presentation.viewmodels.MainViewModel
import com.example.foodapp.ui.theme.GrayBg


@Composable
fun MainScreen(){
    val viewModel: MainViewModel = viewModel()
    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        MainScreenGrid(viewModel = viewModel)

    }
}

@Composable
fun MainScreenGrid(viewModel: MainViewModel){
    Box(){
        val models = viewModel.models.observeAsState(listOf())
        LazyVerticalGrid(columns = GridCells.Fixed(2)){
            items(models.value) {
                model ->
                MainScreenCard(model,
                    onAddButtonClickListener = {
                        viewModel.changeAddPlus(it)
                    })
            }
        }

    }

}


@Composable
fun MainScreenCard(
    productItemModel: ProductItemModel,
    onAddButtonClickListener: (ProductItemModel) -> Unit) {

    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .width(167.dp)
            .background(GrayBg),

        ) {

        Box() {
            val painter = when (productItemModel.productItem?.tag_ids?.get(0)) {
                2 -> painterResource(id = R.drawable.no_meat)
                3 -> painterResource(id = R.drawable.discount)
                4 -> painterResource(id = R.drawable.acute)
                else -> {
                    null
                }
            }
            if (painter != null) {
                Box(
                    modifier = Modifier.padding(8.dp)
                ) {
                    Image(painter = painter, contentDescription = null)
                }

            }

            Column() {
                Image(painter = painterResource(id = R.drawable.previe), contentDescription = null)
                Spacer(modifier = Modifier.height(12.dp))
                Column(modifier = Modifier.padding(start = 12.dp, end = 12.dp, bottom = 12.dp)) {

                    Text(text = productItemModel.productItem.name)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "500 г")
                    Spacer(modifier = Modifier.height(12.dp))


                    Button(modifier = Modifier
                        .height(40.dp)
                        .fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(GrayBg),
                        shape = RoundedCornerShape(8.dp),
                        onClick = { onAddButtonClickListener(productItemModel) }) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = productItemModel.add.toString(), color = Color.Black, fontSize = 16.sp)
                            val pr = productItemModel.add
                            if (productItemModel.productItem.price_old != null) {
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "800 ₽", color = Color.Black,
                                    modifier = Modifier.alpha(0.5f),
                                    textDecoration = TextDecoration.LineThrough,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Normal
                                )
                            }
                        }


                    }
                }
            }
        }
    }
}

