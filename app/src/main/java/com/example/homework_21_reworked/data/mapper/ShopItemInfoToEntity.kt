package com.example.homework_21_reworked.data.mapper

import com.example.homework_21_reworked.data.local.entity.ShopItemEntity
import com.example.homework_21_reworked.domain.model.ShopItemInfo

fun ShopItemInfo.toEntity() : ShopItemEntity {
    return ShopItemEntity(
        id = id,
        cover = cover,
        price = price,
        title = title,
        favorite = favorite,
        category = category
    )
}