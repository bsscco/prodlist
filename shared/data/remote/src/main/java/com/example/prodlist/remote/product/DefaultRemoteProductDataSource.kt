package com.example.prodlist.remote.product

import com.example.prodlist.data.product.CategoriesAndProducts
import com.example.prodlist.data.product.RemoteProductDataSource
import javax.inject.Inject

internal class DefaultRemoteProductDataSource @Inject constructor(
    private val apiProductDataSource: ApiProductDataSource,
) : RemoteProductDataSource {

    override suspend fun getCategoriesAndProducts(): CategoriesAndProducts {
        return apiProductDataSource.getCategoriesAndProducts()
    }
}