package com.example.foodapp.data


import android.app.Application
import android.health.connect.datatypes.units.Mass
import androidx.activity.ComponentActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.foodapp.data.model.category.CategoryItem
import com.example.foodapp.data.model.category.CategoryItemModel
import com.example.foodapp.data.model.product.ProductItem
import com.example.foodapp.data.model.product.ProductItemModel
import com.example.foodapp.data.model.tag.TagsItem
import com.example.foodapp.data.model.tag.TagsItemModel
import com.example.foodapp.ui.presentation.viewmodels.MainViewModel
import com.google.gson.Gson
import java.io.InputStream


class Repository(){


    var ProductsList: List<ProductItem> = listOf()
    var ProductsModelsList: MutableList<ProductItemModel> = mutableListOf()


    fun getListProducts(jsonString: String?): MutableList<ProductItemModel> {
        if (jsonString != null){
            val gson = Gson()
            ProductsList = gson.fromJson(jsonString, Array<ProductItem>::class.java).asList()

            var index = 0
            for (item in ProductsList){
                ProductsModelsList.add(ProductItemModel(item,0, index))
                index++
            }

        }
        return ProductsModelsList
    }

    var CategoriesList: List<CategoryItem> = listOf()
    var CategoriesModelsList: MutableList<CategoryItemModel> = mutableListOf()
    fun getListCategories(jsonString: String?): MutableList<CategoryItemModel> {
        if (jsonString != null){
            val gson = Gson()
            CategoriesList = gson.fromJson(jsonString, Array<CategoryItem>::class.java).asList()

            for (item in CategoriesList){
                CategoriesModelsList.add(CategoryItemModel(item,false))

            }

        }
        return CategoriesModelsList
    }

    var TagsList: List<TagsItem> = listOf()
    var TagsModelsList: MutableList<TagsItemModel> = mutableListOf()
    fun getListTags(jsonString: String?): MutableList<TagsItemModel> {
        if (jsonString != null){
            val gson = Gson()
            TagsList = gson.fromJson(jsonString, Array<TagsItem>::class.java).asList()

            for (item in TagsList){
                TagsModelsList.add(TagsItemModel(item,false))
            }

        }
        return TagsModelsList
    }

}