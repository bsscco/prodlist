package com.example.prodlist.local.di

import android.content.Context
import com.example.prodlist.data.category.LocalCategoryDataSource
import com.example.prodlist.data.product.LocalProductDataSource
import com.example.prodlist.local.category.DefaultLocalCategoryDataSource
import com.example.prodlist.local.category.RoomCategoryDao
import com.example.prodlist.local.db.AppDestructibleDatabase
import com.example.prodlist.local.product.DefaultLocalProductDataSource
import com.example.prodlist.local.product.RoomProductDao
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal abstract class LocalModule {

    @Singleton
    @Binds
    abstract fun provideLocalCategoryDataSource(dataSource: DefaultLocalCategoryDataSource): LocalCategoryDataSource

    @Singleton
    @Binds
    abstract fun provideLocalProductDataSource(dataSource: DefaultLocalProductDataSource): LocalProductDataSource

    companion object {
        @Singleton
        @Provides
        fun provideAppDestructibleDatabase(@ApplicationContext context: Context): AppDestructibleDatabase = AppDestructibleDatabase.buildDatabase(context)

        @Singleton
        @Provides
        fun provideCategoryDao(db: AppDestructibleDatabase): RoomCategoryDao = db.getCategoryDao()

        @Singleton
        @Provides
        fun provideProductDao(db: AppDestructibleDatabase): RoomProductDao = db.getProductDao()
    }
}