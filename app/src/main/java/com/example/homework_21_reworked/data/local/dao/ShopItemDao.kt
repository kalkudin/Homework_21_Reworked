package com.example.homework_21_reworked.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.homework_21_reworked.data.local.entity.ShopItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ShopItemDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(shopItems: List<ShopItemEntity>)

    @Query("SELECT * FROM saved_items")
    fun getAllItems(): Flow<List<ShopItemEntity>>

    @Query("SELECT * FROM saved_items WHERE category LIKE :filter")
    fun getItemsByFilter(filter: String): Flow<List<ShopItemEntity>>
}