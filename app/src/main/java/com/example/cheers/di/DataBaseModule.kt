package com.example.cheers.di

import android.content.Context
import androidx.room.Room
import com.example.cheers.data.localdatabase.CheersDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {

    @Provides
    @Singleton
    fun provideCheersDatabase(@ApplicationContext context:Context):CheersDatabase{
        return Room.databaseBuilder(context,CheersDatabase::class.java,
          "CheersDB").build()

    }
}