package com.example.cheers.data.localdatabase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.cheers.data.localdatabase.entity.FoodPair

@Dao
interface FoodPairDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFoodPair(beers:List<FoodPair>)


}