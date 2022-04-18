package com.example.prodlist.data.product

interface RemoteProductDataSource {

    suspend fun getCategoriesAndProducts(): CategoriesAndProducts
}