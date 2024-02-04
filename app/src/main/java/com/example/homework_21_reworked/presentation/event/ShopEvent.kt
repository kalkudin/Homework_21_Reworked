package com.example.homework_21_reworked.presentation.event

sealed class ShopEvent {
    data object GetShopItems : ShopEvent()
}
