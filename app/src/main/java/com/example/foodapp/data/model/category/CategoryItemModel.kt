package com.example.foodapp.data.model.category

import com.example.foodapp.data.model.product.ProductItem

data class CategoryItemModel(
    val categoryItem: CategoryItem,
    var isSelected: Boolean = false
)