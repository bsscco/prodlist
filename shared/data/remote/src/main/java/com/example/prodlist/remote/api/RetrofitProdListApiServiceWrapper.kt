package com.example.prodlist.remote.api

import com.example.prodlist.remote.product.GetProductionsResponse

internal class RetrofitProdListApiServiceWrapper(
    private val retrofitProdListApiService: RetrofitProdListApiService,
) : ProdListApiService {

    override suspend fun getProductions(): GetProductionsResponse {
        return retrofitProdListApiService.getProductions()
    }
}