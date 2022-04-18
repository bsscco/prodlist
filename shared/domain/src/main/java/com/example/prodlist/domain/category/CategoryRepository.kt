package com.example.prodlist.domain.category

interface CategoryRepository {

    suspend fun getCategories(): List<Category>
}