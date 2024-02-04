package com.example.homework_21_reworked.data.repository

import com.example.homework_21_reworked.data.common.HandleResponse
import com.example.homework_21_reworked.data.common.Resource
import com.example.homework_21_reworked.data.mapper.mapResource
import com.example.homework_21_reworked.data.remote.service.ShopItemService
import com.example.homework_21_reworked.domain.model.ShopItemInfo
import com.example.homework_21_reworked.domain.repository.ShopItemRepositoryRemote
import com.example.homework_21_reworked.data.mapper.toDomain
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ShopItemRepositoryRemoteImpl @Inject constructor(
    private val shopItemService: ShopItemService,
    private val handleResponse: HandleResponse,
) : ShopItemRepositoryRemote {

    override suspend fun getShopItemsFromNetwork(): Flow<Resource<List<ShopItemInfo>>> {
        return handleResponse.handleApiCall { shopItemService.getShopItems() }
            .mapResource { dtoList ->
                dtoList.map { it.toDomain() }
            }
    }
}