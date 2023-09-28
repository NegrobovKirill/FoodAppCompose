package com.example.foodapp.ui.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.foodapp.R
import com.example.foodapp.ui.presentation.viewmodels.MainViewModel
import com.example.foodapp.ui.theme.Dark
import com.example.foodapp.ui.theme.Orange
import java.time.format.TextStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchView(
    textState: MutableState<String>,
    active: MutableState<Boolean>,
    placeHolder: String,

    ) {
        DockedSearchBar(tonalElevation = 0.dp,modifier = Modifier
            .fillMaxHeight()
            .padding(vertical = 4.dp), shape = RectangleShape,
            query = textState.value,
            onQueryChange = {textState.value = it},
            onSearch = {active.value = false},
            active = active.value,
            onActiveChange = {active.value = it},
            placeholder = {
                Text(modifier = Modifier
                    .alpha(0.5f), text = placeHolder, fontSize = 16.sp, fontFamily = FontFamily(Font(R.font.roboto_regular)), color = Dark)
            },
            trailingIcon = {
                if (active.value) {
                    Icon(
                        modifier = Modifier.clickable {
                            if (textState.value.isNotEmpty()) {
                                textState.value = ""
                            } else {
                                active.value = false
                            }
                        },
                        painter = painterResource(id = R.drawable.cancel),
                        contentDescription = null
                    )
                }

            }
        ) {

        }

    }



@Composable
fun SearchScreenGrid(viewModel: MainViewModel, navController: NavController) {

    val models = viewModel.models.observeAsState(mutableListOf())
    val price = viewModel.price.observeAsState()

    var textState = remember {
        mutableStateOf("")
    }
    val active = remember {
        mutableStateOf(false)
    }
    var searchedText = ""

    Box {
        Column(verticalArrangement = Arrangement.SpaceBetween) {
            Column {
                Row(modifier = Modifier
                    .height(64.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(modifier = Modifier.size(24.dp),
                            onClick = { navController.popBackStack() }
                        )
                        {
                            Icon( painter = painterResource(id = R.drawable.back),
                                contentDescription = null, tint = Orange
                            )
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        SearchView(
                            textState,active,
                            placeHolder = "Найти блюдо"
                        )
                        searchedText = textState.value
                    }

                }


            }
            //Для корректного отображения LazyGrid и ShoppingButtom
            val height = if (price.value != 0) {
                72.dp
            } else {
                0.dp
            }

                LazyVerticalGrid(
                    modifier = Modifier.padding(
                        top = 12.dp,
                        start = 12.dp,
                        end = 12.dp,
                        bottom = height
                    ), columns = GridCells.Fixed(2)
                ) {

                    items(models.value.filter {
                        it.productItem.name.contains(
                            searchedText,
                            ignoreCase = true
                        ) || it.productItem.description.contains(searchedText, ignoreCase = true)

                    }) { model ->
                        MainScreenCard(model, navController,
                            onAddPlusButtonClickListener = { viewModel.changeAddPlus(it) },
                            onAddMinusButtonClickListener = { viewModel.changeAddMinus(it) }
                        )
                    }
                }

        }
        Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.SpaceBetween) {
            Row {}
            ShoppingButton(price, navController)
        }

    }
}
