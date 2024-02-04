package com.example.homework_21_reworked.data.remote.model

data class ShopItemDto(
    val id: Int,
    val cover: String,
    val price: String,
    val title: String,
    val favorite: Boolean,
    val category: String,
)
