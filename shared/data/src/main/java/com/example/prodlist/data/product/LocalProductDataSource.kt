package com.example.prodlist.data.product

import com.example.prodlist.ktutil.flow.Result
import kotlinx.coroutines.flow.Flow

interface LocalProductDataSource {

    fun getFilteredProductsByCategory(categoryKey: String): Flow<Result<List<DataProduct>>>

    fun getFavoriteProducts(): Flow<Result<List<DataProduct>>>

    fun getSearchedFavoriteProducts(keyword: String): Flow<Result<List<DataProduct>>>

    fun hasAnyProduct(): Flow<Boolean>

    suspend fun hasProduct(productKey: String): Boolean

    fun getProduct(productKey: String): Flow<Result<DataProduct>>

    suspend fun setProducts(products: List<DataProduct>)

    suspend fun setLike(productKey: String, like: Boolean)
}