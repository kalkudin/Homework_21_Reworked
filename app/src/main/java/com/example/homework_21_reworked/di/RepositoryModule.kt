package com.example.homework_21_reworked.di

import com.example.homework_21_reworked.data.common.HandleResponse
import com.example.homework_21_reworked.data.local.dao.ShopItemDao
import com.example.homework_21_reworked.data.remote.service.ShopItemService
import com.example.homework_21_reworked.data.repository.ShopItemRepositoryLocalImpl
import com.example.homework_21_reworked.data.repository.ShopItemRepositoryRemoteImpl
import com.example.homework_21_reworked.domain.repository.ShopItemRepositoryLocal
import com.example.homework_21_reworked.domain.repository.ShopItemRepositoryRemote
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideShopItemRepositoryRemote(shopItemService: ShopItemService, handleResponse: HandleResponse) : ShopItemRepositoryRemote {
        return ShopItemRepositoryRemoteImpl(shopItemService = shopItemService, handleResponse = handleResponse)
    }

    @Provides
    @Singleton
    fun provideShopItemRepositoryLocal(shopItemDao : ShopItemDao) : ShopItemRepositoryLocal {
        return ShopItemRepositoryLocalImpl(shopItemDao = shopItemDao)
    }
}