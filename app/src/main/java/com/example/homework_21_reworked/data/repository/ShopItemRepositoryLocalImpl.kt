package com.example.homework_21_reworked.data.repository

import com.example.homework_21_reworked.data.local.dao.ShopItemDao
import com.example.homework_21_reworked.data.mapper.toDomain
import com.example.homework_21_reworked.data.mapper.toEntity
import com.example.homework_21_reworked.domain.model.ShopItemInfo
import com.example.homework_21_reworked.domain.repository.ShopItemRepositoryLocal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ShopItemRepositoryLocalImpl @Inject constructor(
    private val shopItemDao: ShopItemDao,
) : ShopItemRepositoryLocal {

    override fun getShopItemsFromDataBase(): Flow<List<ShopItemInfo>> {
        return shopItemDao.getAllItems()
            .map { entities -> entities.map { it.toDomain() } }
    }

    override fun getShopItemsByFilter(filter: String): Flow<List<ShopItemInfo>> {
        return shopItemDao.getItemsByFilter(filter)
            .map { entities -> entities.map { it.toDomain() } }
    }

    override suspend fun saveShopItemsToDataBase(shopItems: List<ShopItemInfo>) {
        shopItemDao.insertAll(shopItems.map { it.toEntity() })
    }
}