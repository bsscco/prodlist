package com.example.prodlist.data.category

interface LocalCategoryDataSource {

    suspend fun getCategories(): List<DataCategory>

    suspend fun setCategories(categories: List<DataCategory>)
}