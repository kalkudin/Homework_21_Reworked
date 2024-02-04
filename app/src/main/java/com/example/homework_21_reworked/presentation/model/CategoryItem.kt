package com.example.homework_21_reworked.presentation.model


data class CategoryItem(
    val id : Int,
    val path : Int,
    val category : Category,
) {
    enum class Category {
        PARTY,
        CAMPING,
        BLOUSE,
        HIKING
    }
}
