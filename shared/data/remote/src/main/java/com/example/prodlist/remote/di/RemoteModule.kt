package com.example.prodlist.remote.di

import com.example.prodlist.data.product.RemoteProductDataSource
import com.example.prodlist.remote.BuildConfig
import com.example.prodlist.remote.api.*
import com.example.prodlist.remote.product.DefaultRemoteProductDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal abstract class RemoteModule {

    @Singleton
    @Binds
    abstract fun provideRemoteProductDataSource(dataSource: DefaultRemoteProductDataSource): RemoteProductDataSource

    companion object {
        @Singleton
        @ProductionApiService
        @Provides
        fun provideProductionRunsApiService(): ProdListApiService = RetrofitProdListApiServiceWrapper(
            Retrofit.Builder()
                .client(createHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://091d0619-b76d-402f-9e9b-17449c3f2029.mock.pstmn.io/")
                .build()
                .create(RetrofitProdListApiService::class.java)
        )

        @Singleton
        @DevApiService
        @Provides
        fun provideDevRunsApiService(): ProdListApiService = RetrofitProdListApiServiceWrapper(
            Retrofit.Builder()
                .client(createHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://091d0619-b76d-402f-9e9b-17449c3f2029.mock.pstmn.io/")
                .build()
                .create(RetrofitProdListApiService::class.java)
        )

        private fun createHttpClient() = OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE)
            )
            .callTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build()
    }
}