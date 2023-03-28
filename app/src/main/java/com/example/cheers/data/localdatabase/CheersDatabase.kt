package com.example.cheers.data.localdatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cheers.data.localdatabase.dao.BeerListDao
import com.example.cheers.data.localdatabase.entity.BeerList

@Database(entities = [BeerList::class], version = 1)
abstract class CheersDatabase:RoomDatabase() {
    abstract fun beeListDao(): BeerListDao

}