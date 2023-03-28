package com.example.cheers.util

import com.example.cheers.data.localdatabase.entity.BeerList
import com.example.cheers.model.dataModel.BeerDataModel
import com.example.cheers.model.dataModel.BeerDetailModel
import com.example.cheers.model.dataModel.FoodPairedBeerDataModel
import com.example.cheers.model.response.BeerDetailResponse
import com.example.cheers.model.response.BeersResponseModel
import com.example.cheers.model.response.FoodPairedBeerResponseModel


fun BeersResponseModel.toViewModel():BeerDataModel{
        return BeerDataModel(
            id,name,tagline,description,image_url?:"")
    }
fun BeerList.toViewModel():BeerDataModel{
    return BeerDataModel(
        id,name,tagline,description,image_url?:"")

}
 fun  BeerDataModel.toEntity(): BeerList {
    return BeerList(
        id,name,tagline,description,image_url
    )
}
fun FoodPairedBeerResponseModel.toViewModel():FoodPairedBeerDataModel{
    return FoodPairedBeerDataModel(id, name, first_brewed, description, image_url, food_pairing)
}

fun BeerDetailResponse.toViewModel():BeerDetailModel{
    return BeerDetailModel(id, name, first_brewed, description, image_url, tagline)
}