package com.example.prodlist.data.category

import com.example.prodlist.data.product.RemoteProductDataSource
import com.example.prodlist.domain.category.Category
import com.example.prodlist.domain.category.CategoryRepository
import javax.inject.Inject

internal class DefaultCategoryRepository @Inject constructor(
    private val localCategoryDataSource: LocalCategoryDataSource,
    private val remoteProductDataSource: RemoteProductDataSource,
) : CategoryRepository {

    override suspend fun getCategories(): List<Category> {
        var localCategories = localCategoryDataSource.getCategories()
        if (localCategories.isEmpty()) {
            val remoteCategories = remoteProductDataSource.getCategoriesAndProducts().categories
            localCategoryDataSource.setCategories(remoteCategories)
            localCategories = remoteCategories
        }
        return localCategories.toDomain()
    }
}