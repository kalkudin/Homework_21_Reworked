package com.example.homework_21_reworked.domain.repository

import com.example.homework_21_reworked.data.common.Resource
import com.example.homework_21_reworked.domain.model.ShopItemInfo
import kotlinx.coroutines.flow.Flow

interface ShopItemRepositoryRemote {
    suspend fun getShopItemsFromNetwork() : Flow<Resource<List<ShopItemInfo>>>
}