package com.example.prodlist.local.category

import com.example.prodlist.data.category.DataCategory
import com.example.prodlist.data.category.LocalCategoryDataSource
import javax.inject.Inject

internal class DefaultLocalCategoryDataSource @Inject constructor(
    private val categoryDao: RoomCategoryDao,
) : LocalCategoryDataSource {

    override suspend fun getCategories(): List<DataCategory> {
        return categoryDao.getCategories().map { it.toData() }
    }

    override suspend fun setCategories(categories: List<DataCategory>) {
        categoryDao.setCategories(categories.map { it.toEntity() })
    }
}