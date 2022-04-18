package com.example.prodlist.data.di

import com.example.prodlist.data.category.DefaultCategoryRepository
import com.example.prodlist.data.product.DefaultProductRepository
import com.example.prodlist.domain.category.CategoryRepository
import com.example.prodlist.domain.product.ProductRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal abstract class DataModule {

    @Singleton
    @Binds
    abstract fun provideCategoryRepository(repository: DefaultCategoryRepository): CategoryRepository

    @Singleton
    @Binds
    abstract fun provideProductRepository(repository: DefaultProductRepository): ProductRepository
}