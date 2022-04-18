package com.example.prodlist.local.product

import com.example.prodlist.data.product.DataProduct
import com.example.prodlist.data.product.LocalProductDataSource
import com.example.prodlist.ktutil.flow.Result
import com.example.prodlist.ktutil.flow.withResult
import com.example.prodlist.local.product.like.ProdLikeEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class DefaultLocalProductDataSource @Inject constructor(
    private val roomProductDao: RoomProductDao,
) : LocalProductDataSource {

    override fun getFilteredProductsByCategory(categoryKey: String): Flow<Result<List<DataProduct>>> {
        return roomProductDao.getFilteredProductsByCategory(categoryKey)
            .map { products -> withResult { products.map { it.toData() } } }
    }

    override fun getFavoriteProducts(): Flow<Result<List<DataProduct>>> {
        return roomProductDao.getFavoriteProducts()
            .map { products -> withResult { products.map { it.toData() } } }
    }

    override fun getSearchedFavoriteProducts(keyword: String): Flow<Result<List<DataProduct>>> {
        return roomProductDao.getSearchedFavoriteProducts(keyword)
            .map { products -> withResult { products.map { it.toData() } } }
    }

    override fun hasAnyProduct(): Flow<Boolean> {
        return roomProductDao.hasAnyProduct()
    }

    override suspend fun hasProduct(productKey: String): Boolean {
        return roomProductDao.hasProduct(productKey)
    }

    override fun getProduct(productKey: String): Flow<Result<DataProduct>> {
        return roomProductDao.getProduct(productKey)
            .filterNotNull()
            .map { product -> withResult { product.toData() } }
    }

    override suspend fun setProducts(products: List<DataProduct>) {
        roomProductDao.setProducts(products.map { it.toEntity() })
    }

    override suspend fun setLike(productKey: String, like: Boolean) {
        roomProductDao.setLike(ProdLikeEntity(productKey, like))
    }
}