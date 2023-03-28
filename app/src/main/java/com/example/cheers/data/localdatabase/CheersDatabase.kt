package com.example.cheers.data.localdatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cheers.data.localdatabase.dao.BeerListDao
import com.example.cheers.data.localdatabase.dao.FoodPairDao
import com.example.cheers.data.localdatabase.entity.BeerList
import com.example.cheers.data.localdatabase.entity.FoodPair

@Database(entities = [BeerList::class,FoodPair::class], version = 1)
abstract class CheersDatabase:RoomDatabase() {
    abstract fun beeListDao():BeerListDao
    abstract fun foodPairDao():FoodPairDao
}