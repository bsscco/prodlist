package com.example.prodlist.data.product

import com.example.prodlist.data.category.DataCategory

data class CategoriesAndProducts(
    val categories: List<DataCategory>,
    val products: List<DataProduct>,
)