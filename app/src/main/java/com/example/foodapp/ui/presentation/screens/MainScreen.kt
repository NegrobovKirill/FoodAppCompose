package com.example.foodapp.ui.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.foodapp.R
import com.example.foodapp.data.model.tag.TagsItemModel
import com.example.foodapp.ui.presentation.viewmodels.MainViewModel
import com.example.foodapp.ui.theme.Dark
import com.example.foodapp.ui.theme.Orange


@Composable
fun MainScreen(viewModel: MainViewModel){



    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "main_screen") {
        composable(route = "main_screen") {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                MainScreenGrid(viewModel = viewModel, navController = navController)

            }
        }
        composable(route = "detail_screen/{id}", arguments = listOf(
            navArgument(name = "id") {
                type = NavType.IntType
            })
        ) {
            DetailScreen(viewModel = viewModel, id = it.arguments?.getInt("id"), navController = navController)
        }
        composable(route = "shopping_screen"){
            ShoppingScreen(viewModel = viewModel, navController = navController)
        }
        composable(route = "search_screen"){
            SearchScreenGrid(viewModel = viewModel, navController = navController)
        }
    }

}


@Composable
fun MainScreenGrid(viewModel: MainViewModel, navController: NavController) {

    val models = viewModel.modelsSelectedWithTags.observeAsState(mutableListOf())
    val filters = viewModel.tagsFilter.observeAsState(mutableListOf())
    val price = viewModel.price.observeAsState()


    Box(modifier = Modifier.padding(top = 16.dp)) {
        Column(verticalArrangement = Arrangement.SpaceBetween) {
            Column {
                Row(modifier = Modifier.fillMaxWidth().height(44.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                    BottomSheet(viewModel, filters)

                    Icon(modifier = Modifier.fillMaxHeight(),
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = null,
                        tint = Orange)

                    IconButton(modifier = Modifier.height(44.dp).background(Color.White).padding(horizontal = 8.dp),
                        onClick = { navController.navigate("search_screen") }) {
                        Icon(painter = painterResource(id = R.drawable.find), contentDescription = null)
                    }
                }



                Row(modifier = Modifier.padding(start = 16.dp, top = 8.dp)) {
                    ChipFilter(viewModel)
                }
            }

            //Для корректного отображения LazyGrid и ShoppingButtom
            val height = if (price.value != 0) {
                72.dp
            } else {
                0.dp
            }

            if (models.value.size == 0) {
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Таких блюд нет :(\n" + "Попробуйте изменить фильтры",
                            fontSize = 16.sp,
                            modifier = Modifier.alpha(0.5f),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            } else {

            LazyVerticalGrid(
                modifier = Modifier.padding(
                    top = 12.dp,
                    start = 12.dp,
                    end = 12.dp,
                    bottom = height
                ), columns = GridCells.Fixed(2)
            ) {

                items(models.value) { model ->
                    MainScreenCard(model, navController,
                        onAddPlusButtonClickListener = { viewModel.changeAddPlus(it) },
                        onAddMinusButtonClickListener = { viewModel.changeAddMinus(it) }
                    )
                }
            }
        }
    }
    Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.SpaceBetween) {
        Row {}
        ShoppingButton(price, navController)
    }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(viewModel: MainViewModel, filters: State<MutableList<TagsItemModel>>) {

    var openBottomSheet by remember { mutableStateOf(false) }

    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    Row(modifier = Modifier
        .height(44.dp)
        .background(Color.White)
        .padding(horizontal = 8.dp)) {
        IconButton(
            modifier = Modifier
                .size(44.dp)
                .background(Color.White),
            onClick = { openBottomSheet = true }) {
            Icon(painter = painterResource(id = R.drawable.settings), contentDescription = null)
        }
        if (openBottomSheet) {
            ModalBottomSheet(sheetState = bottomSheetState,
                onDismissRequest = { openBottomSheet = false }) {
                Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                    Text(
                        text = "Подобрать блюда",
                        fontSize = 20.sp,
                        fontFamily = FontFamily(Font(R.font.roboto_medium)),
                        color = Dark
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    var line = 0

                    for (items in filters.value) {
                        Column() {
                            Row(
                                modifier = Modifier
                                    .height(48.dp)
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = items.tagsItem.name,
                                    fontSize = 16.sp,
                                    fontFamily = FontFamily(Font(R.font.roboto_regular)),
                                    color = Dark
                                )

                                Checkbox(
                                    colors = CheckboxDefaults.colors(
                                        checkedColor = Orange,
                                        uncheckedColor = Dark.copy(alpha = 0.5f)
                                    ),
                                    checked = items.isSelected,
                                    onCheckedChange = {
                                        viewModel.updateTagsFilter(items)
                                    }
                                )

                            }
                        }
                        if (line < filters.value.size - 1) {
                            Divider()
                        }
                        line++
                    }
                    Spacer(modifier = Modifier.height(16.dp))
//                                Button(onClick = { openBottomSheet = false }) {
                    Button(modifier = Modifier
                        .height(48.dp)
                        .fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(Orange),
                        shape = RoundedCornerShape(8.dp),
                        onClick = {
                            openBottomSheet = false
                        }
                    ) {
                        Text(
                            text = "Готово",
                            color = Color.White,
                            fontSize = 16.sp
                        )
                    }
//                                }
                    Spacer(modifier = Modifier.height(48.dp))
                }

            }
        }
    }
}

@Composable
fun ShoppingButton(
    price: State<Int?>,
    navController: NavController
){
    if (price.value != 0) {
        Button(modifier = Modifier
            .height(72.dp)
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 12.dp),
            colors = ButtonDefaults.buttonColors(Orange),
            shape = RoundedCornerShape(8.dp),
            onClick = {navController.navigate(route = "shopping_screen")}) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(painter = painterResource(id = R.drawable.basket), contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = (price.value?.div(100)).toString() + " ₽",
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_medium)),
                    color = Color.White
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChipFilter(viewModel: MainViewModel){

    val categoryItemsList = viewModel.chipFilter.observeAsState(mutableListOf()).value

    Row(modifier = Modifier
        .height(40.dp)
        .horizontalScroll(rememberScrollState())
        .background(Color.White)) {

//        val list = viewModel.chipFilter

        for (item in categoryItemsList){
            SuggestionChip(shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(end = 8.dp),
                onClick = {
                    viewModel.changeChipSelector(item)

                },
                border = if (item.isSelected){SuggestionChipDefaults.suggestionChipBorder(borderColor = Orange,borderWidth = 0.dp)}else{SuggestionChipDefaults.suggestionChipBorder(borderColor = Color.White,borderWidth = 0.dp)},
                colors = if (item.isSelected){SuggestionChipDefaults.suggestionChipColors(containerColor = Orange, labelColor = Color.White)}else{SuggestionChipDefaults.suggestionChipColors(containerColor = Color.White, labelColor = Dark)},
                label = { Text(text = item.categoryItem.name, fontSize = 16.sp, fontFamily = FontFamily(Font(R.font.roboto_medium)))})
        }

    }
}

