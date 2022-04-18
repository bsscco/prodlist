package com.example.prodlist.local.category

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

@Dao
internal interface RoomCategoryDao {

    @Query("DELETE FROM categories")
    suspend fun clearCenters()

    @Transaction
    suspend fun setCategories(categories: List<CategoryEntity>) {
        clearCenters()
        insertCategories(categories)
    }

    @Insert
    suspend fun insertCategories(categories: List<CategoryEntity>)

    @Query("SELECT * FROM categories")
    suspend fun getCategories(): List<CategoryEntity>
}