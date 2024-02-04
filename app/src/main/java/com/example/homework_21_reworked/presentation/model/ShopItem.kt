package com.example.homework_21_reworked.presentation.model

data class ShopItem(
    val id : Int,
    val image : String,
    val price : String,
    val title : String,
    val favorite : Favorite,
    val category : String
) {
    enum class Favorite {
        FAVORITE,
        NOT_FAVORITE
    }
}