package com.example.homework_21_reworked.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.homework_21_reworked.data.local.dao.ShopItemDao
import com.example.homework_21_reworked.data.local.entity.ShopItemEntity

@Database(entities = [ShopItemEntity::class], version = 1)
abstract class ShopItemDataBase : RoomDatabase(){
    abstract fun shopItemDao(): ShopItemDao
}