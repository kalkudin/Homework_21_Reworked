package com.example.homework_21_reworked.domain.model

data class ShopItemInfo(
    val id: Int,
    val cover: String,
    val price: String,
    val title: String,
    val favorite: Boolean,
    val category: String
)