package com.example.homework_21_reworked.presentation.mapper

import com.example.homework_21_reworked.domain.model.ShopItemInfo
import com.example.homework_21_reworked.presentation.model.ShopItem
import java.util.Locale

fun shopItemToState(shopItem: ShopItemInfo): ShopItem {
    return ShopItem(
        id = shopItem.id,
        image = shopItem.cover,
        price = shopItem.price,
        title = shopItem.title,
        favorite = if (shopItem.favorite) ShopItem.Favorite.FAVORITE else ShopItem.Favorite.NOT_FAVORITE,
        category = mapCategory(shopItem.category)
    )
}

private fun mapCategory(category: String): ShopItem.ItemCategory {
    return when (category.uppercase(Locale.ROOT)) {
        "PARDY" -> ShopItem.ItemCategory.PARTY
        "CAMPING" -> ShopItem.ItemCategory.CAMPING
        "BLOUSE" -> ShopItem.ItemCategory.BLOUSE
        "HIKING" -> ShopItem.ItemCategory.HIKING
        else -> throw IllegalArgumentException("Unknown category: $category")
    }
}