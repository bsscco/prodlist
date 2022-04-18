package com.example.prodlist.data.category

import com.example.prodlist.domain.category.Category
import com.example.prodlist.domain.category.CategoryRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

internal class InMemoryCategoryRepository @Inject constructor() : CategoryRepository {

    override suspend fun getCategories(): List<Category> {
        delay(1000L)
        return (1..10).map {
            Category(
                key = it.toString(),
                name = it.toString().repeat(4),
            )
        }
    }
}