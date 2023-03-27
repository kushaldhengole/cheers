package com.example.cheers.data.nerwork

import com.example.cheers.model.response.BeerDetailResponse
import com.example.cheers.model.response.BeersResponseModel
import com.example.cheers.model.response.FoodPairedBeerResponseModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Query

interface CheersApiService {

    @GET("beers")
    suspend fun getBeersList():Response<List<BeersResponseModel>>

    @GET("beers")
    suspend fun getFoodPairedBeer(@Query("per_page") pageSize:Int,
                                  @Query("page")currentPage:Int):Response<List<FoodPairedBeerResponseModel>>

    @GET("beers/random")
    suspend fun getRandomBeersList():Response<List<BeersResponseModel>>

    @GET("beers")
    suspend fun getBeerDetail(@Query("ids") id:Long):Response<List<BeerDetailResponse>>
}