package com.example.homework_21_reworked.presentation.event

sealed class ShopEvent {
    data object GetShopItems : ShopEvent()
    data object GetAllItems : ShopEvent()
    data class GetShopItemsByCategory(val category : String) : ShopEvent()
}
