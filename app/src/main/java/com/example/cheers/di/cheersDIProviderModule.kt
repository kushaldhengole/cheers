package com.example.cheers.di

import com.example.cheers.data.localdatabase.CheersDatabase
import com.example.cheers.data.localdatabase.dao.BeerListDao
import com.example.cheers.data.nerwork.CheersApiService
import com.example.cheers.data.respository.CheersRespoInterface
import com.example.cheers.data.respository.CheersRespository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class cheersDIProviderModule {

    @Provides
    fun provideCheersApiService(retrofit: Retrofit):CheersApiService{
        return retrofit.create(CheersApiService::class.java)
    }

    @Provides
    fun provideCheersListDao(cheersDatabase: CheersDatabase): BeerListDao = cheersDatabase.beeListDao()

    @Provides
    fun provideCheersRespository(
        cheersApiService: CheersApiService,
        cheersDatabase: BeerListDao
    ):CheersRespoInterface{
        return CheersRespository(cheersApiService,cheersDatabase)
    }

}