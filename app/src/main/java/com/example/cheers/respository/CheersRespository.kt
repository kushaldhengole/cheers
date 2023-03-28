package com.example.cheers.respository

import com.example.cheers.core.repositoryOperation
import com.example.cheers.data.localdatabase.dao.BeerListDao
import com.example.cheers.data.localdatabase.entity.BeerList
import com.example.cheers.data.nerwork.CheersApiService
import com.example.cheers.model.commonModels.RepositoryResult
import com.example.cheers.model.dataModel.BeerDataModel
import com.example.cheers.model.dataModel.BeerDetailModel
import com.example.cheers.model.dataModel.FoodPairedBeerDataModel
import com.example.cheers.util.toViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class CheersRespository @Inject constructor(
   val apiService: CheersApiService,
  val  cheersDatabase: BeerListDao
): CheersRespoInterface {

    override suspend fun getFoodPairedBeer(
        page: Int,
        pageSize: Int,
        food: String
    ): Flow<RepositoryResult<List<FoodPairedBeerDataModel>?>> {
        return repositoryOperation.internalApiCall(
            {
                apiService.getFoodPairedBeer(pageSize,page)
            },{
                    data->
                data.map {
                    it.toViewModel()
                }
            }
        )
    }


    override suspend fun getRandomBeer(): Flow<RepositoryResult<BeerDataModel?>> {
      return repositoryOperation.internalApiCall({
          apiService.getRandomBeersList()
      },{
          data->
          data.map {it.toViewModel() }[0]

      })
    }
    override suspend fun getBeerList(isConnected:Boolean): Flow<RepositoryResult<List<BeerDataModel>?>> {
        return if(isConnected){
                getRemoteBeerList()
            }
        else
            getLocalBeerList()

    }


    override suspend fun getRemoteBeerList(): Flow<RepositoryResult<List<BeerDataModel>?>> {
        return repositoryOperation.internalApiCall({
            apiService.getBeersList()
        },{
                data->
               data.map {
                it.toViewModel()
            }
        }
        )
    }

    override suspend fun insertBeerDataLocally(beerEntity: List<BeerList>) {
        cheersDatabase.insertBeers(beerEntity)
    }

    override suspend fun getLocalBeerList(): Flow<RepositoryResult<List<BeerDataModel>>> {
       return repositoryOperation.internalDbCall(
            {
            cheersDatabase.getLocalBeerlist()
            }
        ,{
            data->
               data.map {
                   it.toViewModel()
               }

            })
    }

    override suspend fun getSelectedBeerDetail(id: Long): Flow<RepositoryResult<BeerDetailModel>> {
       return repositoryOperation.internalApiCall({
           apiService.getBeerDetail(id)
       },{
           data-> data.map {
               it.toViewModel()
       }[0]
       })
    }

    override suspend fun deleteBeers() {
        cheersDatabase.deleteBeers()
    }



}