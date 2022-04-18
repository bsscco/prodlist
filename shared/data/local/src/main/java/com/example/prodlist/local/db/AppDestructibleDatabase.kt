package com.example.prodlist.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.prodlist.local.category.CategoryEntity
import com.example.prodlist.local.category.RoomCategoryDao
import com.example.prodlist.local.product.ProductEntity
import com.example.prodlist.local.product.RoomProductDao
import com.example.prodlist.local.product.like.ProdLikeEntity

@Database(
    entities = [
        CategoryEntity::class,
        ProductEntity::class,
        ProdLikeEntity::class,
    ],
    version = 2,
    exportSchema = false,
)
abstract class AppDestructibleDatabase : RoomDatabase() {

    internal abstract fun getCategoryDao(): RoomCategoryDao

    internal abstract fun getProductDao(): RoomProductDao

    companion object {
        private const val DATABASE_NAME = "room-destructible-db"

        fun buildDatabase(context: Context): AppDestructibleDatabase {
            return Room.databaseBuilder(context, AppDestructibleDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}