package com.example.foodapp.network

import com.example.foodapp.data.model.category.Category
import com.example.foodapp.data.model.product.Products
import com.example.foodapp.data.model.tag.Tags
import retrofit2.http.GET

interface MainApi {
    @GET("products")
    suspend fun getAllProducts(): Products

    @GET("categories")
    suspend fun getAllCategories(): Category

    @GET("tags")
    suspend fun getAllTags(): Tags
}