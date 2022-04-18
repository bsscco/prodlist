package com.example.prodlist.remote.api

import com.example.prodlist.remote.product.GetProductionsResponse

internal interface ProdListApiService {

    suspend fun getProductions(): GetProductionsResponse
}