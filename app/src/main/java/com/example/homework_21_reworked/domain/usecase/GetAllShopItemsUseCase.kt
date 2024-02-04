package com.example.homework_21_reworked.domain.usecase

import com.example.homework_21_reworked.data.common.Resource
import com.example.homework_21_reworked.domain.model.ShopItemInfo
import com.example.homework_21_reworked.domain.repository.ShopItemRepositoryLocal
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllShopItemsUseCase @Inject constructor(
    private val shopItemRepositoryLocal: ShopItemRepositoryLocal
) {
    operator fun invoke(): Flow<Resource<List<ShopItemInfo>>> {
        return shopItemRepositoryLocal.getShopItemsFromDataBase()
    }
}