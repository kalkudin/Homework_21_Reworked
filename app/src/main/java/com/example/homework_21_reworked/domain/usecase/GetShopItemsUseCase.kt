package com.example.homework_21_reworked.domain.usecase

import com.example.homework_21_reworked.data.common.ErrorType
import com.example.homework_21_reworked.data.common.Resource
import com.example.homework_21_reworked.domain.model.ShopItemInfo
import com.example.homework_21_reworked.domain.repository.ShopItemRepositoryLocal
import com.example.homework_21_reworked.domain.repository.ShopItemRepositoryRemote
import com.example.homework_21_reworked.domain.util.NetworkStatusTracker
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetShopItemsUseCase @Inject constructor(
    private val shopItemRepositoryRemote: ShopItemRepositoryRemote,
    private val shopItemRepositoryLocal: ShopItemRepositoryLocal,
    private val networkStatusTracker: NetworkStatusTracker
) {
    operator fun invoke(): Flow<Resource<List<ShopItemInfo>>> = flow {
        if (networkStatusTracker.isConnected()) {
            val remoteFlow = shopItemRepositoryRemote.getShopItemsFromNetwork()
            emitAll(remoteFlow)
        } else {
            val localItems = shopItemRepositoryLocal.getShopItemsFromDataBase().first()
            if (localItems.isEmpty()) {
                emit(Resource.Error(-1, ErrorType.EmptyDatabase))
            } else {
                emit(Resource.Success(localItems))
            }
        }
    }
}