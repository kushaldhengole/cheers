package com.example.cheers.data.localdatabase.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "beerList")
data class BeerList (
    @PrimaryKey(autoGenerate = false)
    val id:Long?,
    val name:String?,
    val tagline:String?,
    val description:String?,
    val image_url:String?
)
