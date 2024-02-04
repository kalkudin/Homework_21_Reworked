package com.example.homework_21_reworked.data.repository

import android.util.Log
import com.example.homework_21_reworked.data.common.ErrorType
import com.example.homework_21_reworked.data.common.Resource
import com.example.homework_21_reworked.data.local.dao.ShopItemDao
import com.example.homework_21_reworked.data.mapper.toDomain
import com.example.homework_21_reworked.data.mapper.toEntity
import com.example.homework_21_reworked.domain.model.ShopItemInfo
import com.example.homework_21_reworked.domain.repository.ShopItemRepositoryLocal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

class ShopItemRepositoryLocalImpl @Inject constructor(
    private val shopItemDao: ShopItemDao,
) : ShopItemRepositoryLocal {

    override fun getShopItemsFromDataBase(): Flow<Resource<List<ShopItemInfo>>> {
        return shopItemDao.getAllItems()
            .transform { entities ->
                if (entities.isEmpty()) {
                    emit(Resource.Error(-1, ErrorType.EmptyDatabase))
                } else {
                    emit(Resource.Success(entities.map { it.toDomain() }))
                }
            }
    }

    override fun getShopItemsByFilter(filter: String): Flow<Resource<List<ShopItemInfo>>> {
        return shopItemDao.getItemsByFilter(filter)
            .transform { entities ->
                if (entities.isEmpty()) {
                    emit(Resource.Error(-1, ErrorType.EmptyDatabase))
                } else {
                    emit(Resource.Success(entities.map { it.toDomain() }))
                }
            }
    }

    override suspend fun saveShopItemsToDataBase(shopItems: List<ShopItemInfo>) {
        Log.d("LocalRepo", "items saved")
        shopItemDao.insertAll(shopItems.map { it.toEntity() })
    }
}