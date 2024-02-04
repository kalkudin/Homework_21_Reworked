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
        category = shopItem.category
    )
}