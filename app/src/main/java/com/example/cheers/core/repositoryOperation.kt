package com.example.cheers.core

import android.util.Log
import com.example.cheers.model.commonModels.RepositoryResult
import com.example.cheers.respository.Source
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import retrofit2.Response

object repositoryOperation {

    fun<T,P> internalApiCall(
        apiCall:suspend ()-> Response<T>,
        resultProcessor:(T)->P
    ): Flow<RepositoryResult<P>> = flow {
        emit(RepositoryResult.Loading(true, Source.REMOTE))
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
            RepositoryResult.Success(resultProcessor(apiResult.body()!!), Source.REMOTE)
        }else{
            Log.e("result","hh$apiResult")
            RepositoryResult.Error(apiResult.code().toLong(),"Something Went Wrong", Source.REMOTE)
        }
    }
    catch (ex:Exception){
        Log.e("Exception","hh$ex")
        RepositoryResult.Error(404,"Somthing Went Wrong", Source.REMOTE)
    }

    private suspend fun <T, P> safeDbData(dbCall: suspend () -> T, resProcess: (T) -> P): RepositoryResult<P> = try {
        val result =dbCall.invoke()
        Log.e("safeDbData","hh$result")
        RepositoryResult.Success(resProcess.invoke(result), Source.LOCAL)
    }
    catch (ex:java.lang.Exception){
        Log.e("Exception","hh$ex")
        RepositoryResult.Exception(ex, Source.LOCAL)
    }

    fun<T,P> internalDbCall(
        dbCall:suspend ()-> T,
        resProcess: (T)-> P
    ): Flow<RepositoryResult<P>> = flow {
        emit(RepositoryResult.Loading(true, Source.LOCAL))
        delay(100)
        val flow= flowOf (safeDbData(dbCall,resProcess))
        emit(RepositoryResult.Loading(false, Source.LOCAL))
        emitAll(flow)
    }.flowOn(Dispatchers.IO)


}