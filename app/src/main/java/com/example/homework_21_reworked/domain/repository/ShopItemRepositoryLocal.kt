package com.example.homework_21_reworked.domain.repository

import com.example.homework_21_reworked.domain.model.ShopItemInfo
import kotlinx.coroutines.flow.Flow

interface ShopItemRepositoryLocal {
    fun getShopItemsFromDataBase() : Flow<List<ShopItemInfo>>
    fun getShopItemsByFilter(filter: String) : Flow<List<ShopItemInfo>>
    suspend fun saveShopItemsToDataBase(shopItems: List<ShopItemInfo>)
}