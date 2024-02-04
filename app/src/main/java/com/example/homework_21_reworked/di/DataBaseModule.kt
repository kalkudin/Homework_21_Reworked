package com.example.homework_21_reworked.di

import android.content.Context
import androidx.room.Room
import com.example.homework_21_reworked.data.local.dao.ShopItemDao
import com.example.homework_21_reworked.data.local.database.ShopItemDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {
    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context): ShopItemDataBase =
        Room.databaseBuilder(
            context, ShopItemDataBase::class.java, "USER_DATABASE"
        )
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideUserAccountDao(database: ShopItemDataBase): ShopItemDao {
        return database.shopItemDao()
    }
}