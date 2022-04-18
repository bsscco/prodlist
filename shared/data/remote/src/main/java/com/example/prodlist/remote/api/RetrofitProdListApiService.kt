package com.example.prodlist.remote.api

import com.example.prodlist.remote.product.GetProductionsResponse
import retrofit2.http.GET
import retrofit2.http.Headers

internal interface RetrofitProdListApiService {

    @GET("prod/list")
    @Headers("Content-Type: application/json")
    suspend fun getProductions(): GetProductionsResponse
}