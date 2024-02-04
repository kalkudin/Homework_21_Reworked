package com.example.homework_21_reworked.data.remote.service

import com.example.homework_21_reworked.data.remote.model.ShopItemDto
import retrofit2.Response
import retrofit2.http.GET

interface ShopItemService {
    @GET("/v3/df8d4951-2757-45aa-8f60-bf1592a090ce")
    suspend fun getShopItems() : Response<List<ShopItemDto>>
}