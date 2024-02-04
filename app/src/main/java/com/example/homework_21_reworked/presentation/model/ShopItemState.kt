package com.example.homework_21_reworked.presentation.model

data class ShopItemState(
    val isLoading : Boolean = false,
    val isSuccess : List<ShopItem>? = null,
    val isError : String? = null
)