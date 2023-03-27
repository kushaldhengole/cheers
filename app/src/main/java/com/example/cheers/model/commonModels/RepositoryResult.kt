package com.example.cheers.model.commonModels

import com.example.cheers.data.respository.Source

sealed class RepositoryResult <out T>{
    data class Success<out T>(val data:T?, val source: Source):RepositoryResult<T>()
    data class Error( val code:Long,val Message:String, val source: Source):RepositoryResult<Nothing>()
    data class Exception(val exception:Throwable, val source: Source):RepositoryResult<Nothing>()
    data class Loading(val isLoading: Boolean, val source: Source):RepositoryResult<Nothing>()

}