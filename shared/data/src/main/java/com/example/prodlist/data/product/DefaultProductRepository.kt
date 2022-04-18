package com.example.prodlist.data.product

import com.example.prodlist.domain.product.Product
import com.example.prodlist.domain.product.ProductRepository
import com.example.prodlist.ktutil.flow.Result
import com.example.prodlist.ktutil.flow.mapSuccessData
import com.example.prodlist.ktutil.flow.withResult
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapConcat
import javax.inject.Inject

internal class DefaultProductRepository @Inject constructor(
    private val localProductDataSource: LocalProductDataSource,
    private val remoteProductDataSource: RemoteProductDataSource,
) : ProductRepository {

    override fun getFilteredProductsByCategory(categoryKey: String): Flow<Result<List<Product>>> {
        return getCachedProducts(localProductDataSource.getFilteredProductsByCategory(categoryKey))
    }

    private fun getCachedProducts(localProducts: Flow<Result<List<DataProduct>>>): Flow<Result<List<Product>>> {
        @OptIn(FlowPreview::class)
        return localProductDataSource.hasAnyProduct()
            .flatMapConcat { hasLocalProduct ->
                if (hasLocalProduct.not()) {
                    val remoteProducts = remoteProductDataSource.getCategoriesAndProducts().products
                    localProductDataSource.setProducts(remoteProducts)
                }
                localProducts
            }
            .mapSuccessData { products -> withResult { products.map { it.toDomain() } } }
    }

    override fun getFavoriteProducts(): Flow<Result<List<Product>>> {
        return getCachedProducts(localProductDataSource.getFavoriteProducts())
    }

    override fun getSearchedFavoriteProducts(keyword: String): Flow<Result<List<Product>>> {
        return getCachedProducts(localProductDataSource.getSearchedFavoriteProducts(keyword))
    }

    override suspend fun isProductExist(productKey: String): Boolean {
        localProductDataSource.hasAnyProduct().filter { hasAny -> hasAny }.first()
        return localProductDataSource.hasProduct(productKey)
    }

    override fun getProduct(productKey: String): Flow<Result<Product>> {
        return localProductDataSource.getProduct(productKey)
            .mapSuccessData { product -> withResult { product.toDomain() } }
    }

    override suspend fun setLike(productKey: String, like: Boolean) {
        localProductDataSource.setLike(productKey, like)
    }
}