package com.example.prodlist.remote.product

import com.example.prodlist.data.product.CategoriesAndProducts
import com.example.prodlist.remote.api.ProdListApiService
import com.example.prodlist.remote.api.ProductionApiService
import com.example.prodlist.remote.error.RemoteDataSourceException
import javax.inject.Inject

internal class ApiProductDataSource @Inject constructor(
    @ProductionApiService private val prodListApiService: ProdListApiService,
) {

    suspend fun getCategoriesAndProducts(): CategoriesAndProducts {
        val response = prodListApiService.getProductions()
        if (response.code != "200") {
            throw RemoteDataSourceException(response.code, response.message!!)
        }
        return response.toData()
    }
}