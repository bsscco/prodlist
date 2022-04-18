package com.example.prodlist.data.product

import com.example.prodlist.data.product.like.ProdLike
import com.example.prodlist.domain.product.Product
import com.example.prodlist.domain.product.ProductRepository
import com.example.prodlist.ktutil.flow.Result
import com.example.prodlist.ktutil.flow.combine
import com.example.prodlist.ktutil.flow.mapSuccessData
import com.example.prodlist.ktutil.flow.withResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class InMemoryProductRepository @Inject constructor() : ProductRepository {

    private val produnctionsFlow = MutableStateFlow<List<DataProduct>>(emptyList())
    private val prodLikesFlow = MutableStateFlow<List<ProdLike>>(emptyList())

    init {
        val products = (1..10).map {
            DataProduct(
                key = it.toString(),
                categoryKey = "1",
                name = it.toString().repeat(4),
                price = it * 10000,
                liked = null,
            )
        } + (11..20).map {
            DataProduct(
                key = it.toString(),
                categoryKey = "2",
                name = it.toString().repeat(4),
                price = it * 10000,
                liked = null,
            )
        }
        produnctionsFlow.value = products
    }

    override fun getFilteredProductsByCategory(categoryKey: String): Flow<Result<List<Product>>> {
        return combine(getProducts(), getProdLikes())
            .mapSuccessData { (products, likes) -> withResult { products.filterByCategory(likes, categoryKey) } }
    }

    private fun getProducts(): Flow<Result.Success<List<DataProduct>>> {
        return produnctionsFlow
            .map { products -> Result.Success(products) }
    }

    private fun getProdLikes(): Flow<Result.Success<List<ProdLike>>> {
        return prodLikesFlow
            .map { likes -> Result.Success(likes) }
    }

    private fun List<DataProduct>.filterByCategory(likes: List<ProdLike>, categoryKey: String): List<Product> {
        val likeMap = likes.associate { it.productKey to it.liked }
        return this
            .filter { product -> product.categoryKey == categoryKey }
            .map { product ->
                Product(
                    key = product.key,
                    categoryKey = product.categoryKey,
                    name = product.name,
                    price = product.price,
                    liked = likeMap.getOrDefault(product.key, false)
                )
            }
    }

    override fun getFavoriteProducts(): Flow<Result<List<Product>>> {
        return combine(getProducts(), getProdLikes())
            .mapSuccessData { (products, likes) -> withResult { products.onlyLiked(likes) } }
    }

    private fun List<DataProduct>.onlyLiked(likes: List<ProdLike>): List<Product> {
        val likeMap = likes.associate { it.productKey to it.liked }
        return this
            .filter { product -> likeMap.getOrDefault(product.key, false) }
            .map { product ->
                Product(
                    key = product.key,
                    categoryKey = product.categoryKey,
                    name = product.name,
                    price = product.price,
                    liked = true,
                )
            }
    }

    override fun getSearchedFavoriteProducts(keyword: String): Flow<Result<List<Product>>> {
        return combine(getProducts(), getProdLikes())
            .mapSuccessData { (products, likes) -> withResult { products.onlyLiked(likes, keyword) } }
    }

    private fun List<DataProduct>.onlyLiked(likes: List<ProdLike>, nameKeyword: String): List<Product> {
        return this@onlyLiked.onlyLiked(likes)
            .filter { product -> product.name.contains(nameKeyword) }
    }

    override fun getProduct(productKey: String): Flow<Result<Product>> {
        return combine(getProducts(), getProdLikes())
            .mapSuccessData { (products, likes) -> withResult { products.getByKey(likes, productKey) } }
    }

    private fun List<DataProduct>.getByKey(likes: List<ProdLike>, productKey: String): Product {
        val likeMap = likes.associate { it.productKey to it.liked }
        val product = first { product -> product.key == productKey }
        return Product(
            key = product.key,
            categoryKey = product.categoryKey,
            name = product.name,
            price = product.price,
            liked = likeMap.getOrDefault(product.key, false)
        )
    }

    override suspend fun isProductExist(productKey: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun setLike(productKey: String, like: Boolean) {
        val likeMap = prodLikesFlow.value.associateBy { it.productKey }.toMutableMap()
        likeMap[productKey] = ProdLike(productKey, like)
        prodLikesFlow.value = likeMap.values.toList()
    }
}