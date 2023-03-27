package com.example.cheers.data.localdatabase.dao

import androidx.room.*
import com.example.cheers.data.localdatabase.entity.BeerList

@Dao
interface BeerListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBeers(beers:List<BeerList>)

    @Query("Select * From BeerList")
    suspend fun getLocalBeerlist():List<BeerList>

    @Query("Select * From BeerList Where id = :id")
    suspend fun getSelectedBeer(id:String):BeerList

    @Query("Delete From BeerList")
    suspend fun deleteBeers()

}