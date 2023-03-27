package com.example.cheers.data.localdatabase.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "foodPairing")
data class FoodPair(
    @PrimaryKey(autoGenerate = true)
    val id:Long,
    val FoodName:String
)