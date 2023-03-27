package com.example.cheers.model.dataModel

data class FoodPairedBeerDataModel(val id:Long?,
                                   val name:String?,
                                   val first_brewed:String?,
                                   val description:String?,
                                   val image_url:String?,
                                   val food_pairing:ArrayList<String>)