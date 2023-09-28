package com.example.foodapp.ui.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.foodapp.R
import com.example.foodapp.data.model.product.ProductItemModel
import com.example.foodapp.ui.theme.Dark
import com.example.foodapp.ui.theme.GrayBg
import com.example.foodapp.ui.theme.Orange

@Composable
fun MainScreenCard(
    productItemModel: ProductItemModel,
    navController: NavController,
    onAddPlusButtonClickListener: (ProductItemModel) -> Unit,
    onAddMinusButtonClickListener: (ProductItemModel) -> Unit) {

    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .background(GrayBg)
            .padding(4.dp)
            .clickable { navController.navigate(route = "detail_screen/${productItemModel.id}") },

        ) {

        Box() {
            if (productItemModel.productItem.tag_ids?.size != 0){
                val painter = when (productItemModel.productItem.tag_ids?.get(0)) {
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
            }


            Column() {
                Image(painter = painterResource(id = R.drawable.previe), contentDescription = null)
                Spacer(modifier = Modifier.height(12.dp))
                Column(modifier = Modifier.padding(start = 12.dp, end = 12.dp, bottom = 12.dp)) {

                    Text(modifier = Modifier.height(40.dp),
                        text = productItemModel.productItem.name,
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.roboto_medium)),
                        color = Dark
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(modifier = Modifier.alpha(0.5f), text = productItemModel.productItem.measure.toString() + " " + productItemModel.productItem.measure_unit,
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.roboto_medium)),
                        color = Dark
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    AddButton(productItemModel = productItemModel,
                        onPlusClick = onAddPlusButtonClickListener,
                        onMinusClick = onAddMinusButtonClickListener
                    )

                }
            }
        }
    }
}

@Composable
fun AddButton(
    productItemModel: ProductItemModel,
    onPlusClick: (ProductItemModel) -> Unit,
    onMinusClick: (ProductItemModel) -> Unit
) {

    if (productItemModel.add == 0) {
        Button(modifier = Modifier
            .height(40.dp)
            .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(GrayBg),
            shape = RoundedCornerShape(8.dp),
            onClick = { onPlusClick(productItemModel) })
        {

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = (productItemModel.productItem.price_current / 100).toString()+ " ₽", color = Dark,
                    fontFamily = FontFamily(Font(R.font.roboto_medium)),
                    fontSize = 16.sp
                )

                if (productItemModel.productItem.price_old != null) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = (productItemModel.productItem.price_old / 100).toString() + " ₽", color = Dark,
                        modifier = Modifier.alpha(0.5f),
                        textDecoration = TextDecoration.LineThrough,
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.roboto_regular))
                    )
                }
            }
        }
    }
    else
    {

        Row(
            modifier = Modifier
                .height(40.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                modifier = Modifier.size(40.dp),
                colors = ButtonDefaults.buttonColors(Color.White),
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(8.dp),
                onClick = { onMinusClick(productItemModel) }) {
                Icon(
                    painter = painterResource(id = R.drawable.minus),
                    contentDescription = null,
                    tint = Orange
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                modifier = Modifier.width(40.dp),
                text = productItemModel.add.toString(),
                fontFamily = FontFamily(Font(R.font.roboto_medium)),
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.width(12.dp))
            Button(
                modifier = Modifier.size(40.dp),
                colors = ButtonDefaults.buttonColors(Color.White),
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(8.dp),
                onClick = { onPlusClick(productItemModel) }) {
                Icon(
                    painter = painterResource(id = R.drawable.plus),
                    contentDescription = null,
                    tint = Orange
                )
            }
        }
    }
}


