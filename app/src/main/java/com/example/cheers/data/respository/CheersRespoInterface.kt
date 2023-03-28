package com.example.cheers.data.respository

import com.example.cheers.data.localdatabase.entity.BeerList
import com.example.cheers.model.commonModels.RepositoryResult
import com.example.cheers.model.dataModel.BeerDataModel
import com.example.cheers.model.dataModel.BeerDetailModel
import com.example.cheers.model.dataModel.FoodPairedBeerDataModel
import kotlinx.coroutines.flow.Flow

interface CheersRespoInterface {

    suspend fun getBeerList(isConnected:Boolean):Flow<RepositoryResult<List<BeerDataModel>?>>

    suspend fun getRemoteBeerList():Flow<RepositoryResult<List<BeerDataModel>?>>

    suspend fun insertBeerDataLocally(beerEntity: List<BeerList>)

    suspend fun getLocalBeerList():Flow<RepositoryResult<List<BeerDataModel>>>

    suspend fun getSelectedBeerDetail(id:Long):Flow<RepositoryResult<BeerDetailModel>>

    suspend fun deleteBeers()

    suspend fun getFoodPairedBeer(page:Int,pageSize:Int,food:String):Flow<RepositoryResult<List<FoodPairedBeerDataModel>?>>

    suspend fun getRandomBeer():Flow<RepositoryResult<BeerDataModel?>>

}