package com.example.homework_21_reworked.presentation.model

data class ShopItem(
    val id : Int,
    val image : String,
    val price : String,
    val title : String,
    val favorite : Favorite,
    val category : ItemCategory
) {
    enum class Favorite {
        FAVORITE,
        NOT_FAVORITE
    }

    enum class ItemCategory {
        PARTY,
        CAMPING,
        BLOUSE,
        HIKING
    }
}