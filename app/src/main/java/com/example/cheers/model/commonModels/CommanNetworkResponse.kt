package com.example.cheers.model.commonModels

import com.google.gson.annotations.SerializedName

data class CommanNetworkResponse (
    @SerializedName("statusCode")
    val statusCode:Long,
    @SerializedName("error")
    val error:String,
    @SerializedName("message")
    val message:String){
}