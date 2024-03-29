package com.example.homework_21_reworked.domain.usecase

import com.example.homework_21_reworked.data.common.Resource
import com.example.homework_21_reworked.domain.model.ShopItemInfo
import com.example.homework_21_reworked.domain.repository.ShopItemRepositoryLocal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetFilteredShopItemsUseCase @Inject constructor(
    private val shopItemRepositoryLocal: ShopItemRepositoryLocal
) {
    operator fun invoke(filter: String): Flow<Resource<List<ShopItemInfo>>> {
        return shopItemRepositoryLocal.getShopItemsByFilter(filter)
    }
}