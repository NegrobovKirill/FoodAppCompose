package com.example.foodapp.network

import com.example.foodapp.data.model.category.Category
import com.example.foodapp.data.model.product.Products
import com.example.foodapp.data.model.tag.Tags
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkContainer {


    private lateinit var products: Products
    private lateinit var category: Category
    private lateinit var tags: Tags

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val productApi = retrofit.create(MainApi::class.java)

    fun addProducts(): Products{
        CoroutineScope(Dispatchers.IO).launch {
            products = productApi.getAllProducts()
        }
        return products
    }

    fun addCategories(): Category{
        CoroutineScope(Dispatchers.IO).launch {
            category = productApi.getAllCategories()
        }
        return category
    }

    fun addTags(): Tags{
        CoroutineScope(Dispatchers.IO).launch {
            tags = productApi.getAllTags()
        }
        return tags
    }

    companion object{
        val BASE_URL = "https//........."
    }
}