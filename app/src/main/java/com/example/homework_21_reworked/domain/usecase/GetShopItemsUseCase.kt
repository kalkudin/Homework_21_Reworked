package com.example.homework_21_reworked.domain.usecase

import com.example.homework_21_reworked.data.common.Resource
import com.example.homework_21_reworked.domain.model.ShopItemInfo
import com.example.homework_21_reworked.domain.repository.ShopItemRepositoryLocal
import com.example.homework_21_reworked.domain.repository.ShopItemRepositoryRemote
import com.example.homework_21_reworked.domain.util.NetworkStatusTracker
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetShopItemsUseCase @Inject constructor(
    private val shopItemRepositoryRemote: ShopItemRepositoryRemote,
    private val shopItemRepositoryLocal: ShopItemRepositoryLocal,
    private val networkStatusTracker: NetworkStatusTracker
) {
    operator fun invoke(): Flow<Resource<List<ShopItemInfo>>> = flow {
        if (networkStatusTracker.isConnected()) {
            shopItemRepositoryRemote.getShopItemsFromNetwork().collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        shopItemRepositoryLocal.saveShopItemsToDataBase(resource.data)
                        emit(resource)
                    }
                    else -> {
                        emit(resource)
                    }
                }
            }
        } else {
            emitAll(shopItemRepositoryLocal.getShopItemsFromDataBase())
        }
    }
}