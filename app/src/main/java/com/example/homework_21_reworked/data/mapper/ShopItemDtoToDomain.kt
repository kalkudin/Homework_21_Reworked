package com.example.homework_21_reworked.data.mapper

import com.example.homework_21_reworked.data.remote.model.ShopItemDto
import com.example.homework_21_reworked.domain.model.ShopItemInfo

fun ShopItemDto.toDomain() : ShopItemInfo {
    return ShopItemInfo(
        id = id,
        cover = cover,
        price = price,
        title = title,
        favorite = favorite,
        category = category
    )
}