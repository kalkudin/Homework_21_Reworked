package com.example.homework_21_reworked.domain.usecase

import com.example.homework_21_reworked.domain.model.ShopItemInfo
import com.example.homework_21_reworked.domain.repository.ShopItemRepositoryLocal
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFilteredShopItemsUseCase @Inject constructor(
    private val shopItemRepositoryLocal: ShopItemRepositoryLocal
) {
    operator fun invoke(filter : String) : Flow<List<ShopItemInfo>> {
        return shopItemRepositoryLocal.getShopItemsByFilter(filter = filter)
    }
}