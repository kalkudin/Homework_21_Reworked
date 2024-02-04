package com.example.homework_21_reworked.domain.repository

import com.example.homework_21_reworked.data.common.Resource
import com.example.homework_21_reworked.domain.model.ShopItemInfo
import kotlinx.coroutines.flow.Flow

interface ShopItemRepositoryLocal {
    fun getShopItemsFromDataBase() : Flow<Resource<List<ShopItemInfo>>>
    fun getShopItemsByFilter(filter: String) : Flow<Resource<List<ShopItemInfo>>>
    suspend fun saveShopItemsToDataBase(shopItems: List<ShopItemInfo>)
}