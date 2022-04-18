package com.example.prodlist.domain.product

import com.example.prodlist.ktutil.flow.Result
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    fun getFilteredProductsByCategory(categoryKey: String): Flow<Result<List<Product>>>

    fun getFavoriteProducts(): Flow<Result<List<Product>>>

    fun getSearchedFavoriteProducts(keyword: String): Flow<Result<List<Product>>>

    suspend fun isProductExist(productKey: String): Boolean

    fun getProduct(productKey: String): Flow<Result<Product>>

    suspend fun setLike(productKey: String, like: Boolean)
}