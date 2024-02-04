package com.example.homework_21_reworked.data.mapper

import com.example.homework_21_reworked.data.local.entity.ShopItemEntity
import com.example.homework_21_reworked.domain.model.ShopItemInfo

fun ShopItemEntity.toDomain() : ShopItemInfo {
    return ShopItemInfo(
        id = id,
        cover = cover,
        price = price,
        title = title,
        favorite = favorite,
        category = category
    )
}