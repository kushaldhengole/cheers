package com.example.cheers.data.respository

import android.util.Log
import com.example.cheers.data.localdatabase.dao.BeerListDao
import com.example.cheers.data.localdatabase.entity.BeerList
import com.example.cheers.data.nerwork.CheersApiService
import com.example.cheers.model.commonModels.RepositoryResult
import com.example.cheers.model.dataModel.BeerDataModel
import com.example.cheers.model.dataModel.BeerDetailModel
import com.example.cheers.model.dataModel.FoodPairedBeerDataModel
import com.example.cheers.util.toViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.awaitResponse
import javax.inject.Inject

class CheersRespository @Inject constructor(
   val apiService: CheersApiService,
  val  cheersDatabase: BeerListDao):CheersRespoInterface{

    override suspend fun getFoodPairedBeer(
        page: Int,
        pageSize: Int,
        food: String
    ): Flow<RepositoryResult<List<FoodPairedBeerDataModel>?>> {
        return internalApiCall(
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
      return internalApiCall({
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
        return internalApiCall({
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
       return internalDbCall(
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
       return internalApiCall({
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



    private fun<T,P> internalApiCall(
        apiCall:suspend ()->Response<T>,
        resultProcessor:(T)->P
    ):Flow<RepositoryResult<P>> = flow {
        emit(RepositoryResult.Loading(true,Source.REMOTE))
        val flow = flowOf(apiResponsParse(apiCall,resultProcessor))
        emit(RepositoryResult.Loading(false, Source.REMOTE))
        emitAll(flow)
    }.flowOn(Dispatchers.IO)

    private suspend fun <T, P> apiResponsParse(apiCall: suspend () -> Response<T>, resultProcessor: (T) -> P)
            = try {

        val apiResult = apiCall.invoke()

        Log.e("result","h${apiResult.body()}")
        if (apiResult.isSuccessful){
            val body= apiResult.body()
            RepositoryResult.Success(resultProcessor(apiResult.body()!!),Source.REMOTE)
        }else{
            Log.e("result","hh$apiResult")
            RepositoryResult.Error(apiResult.code().toLong(),"Something Went Wrong",Source.REMOTE)
        }
    }
    catch (ex:Exception){
        Log.e("Exception","hh$ex")
        RepositoryResult.Error(404,"Somthing Went Wrong",Source.REMOTE)
    }

    private suspend fun <T, P> safeDbData(dbCall: suspend () -> T, resProcess: (T) -> P):RepositoryResult<P> = try {
        val result =dbCall.invoke()
        Log.e("safeDbData","hh$result")
        RepositoryResult.Success(resProcess.invoke(result),Source.LOCAL)
    }
    catch (ex:java.lang.Exception){
        Log.e("Exception","hh$ex")
        RepositoryResult.Exception(ex,Source.LOCAL)
    }

    private fun<T,P> internalDbCall(
        dbCall:suspend ()-> T,
        resProcess: (T)-> P
    ):Flow<RepositoryResult<P>> = flow {
        emit(RepositoryResult.Loading(true,Source.LOCAL))
        kotlinx.coroutines.delay(100)
        val flow= flowOf (safeDbData(dbCall,resProcess))
        emit(RepositoryResult.Loading(false,Source.LOCAL))
        emitAll(flow)
    }.flowOn(Dispatchers.IO)


}