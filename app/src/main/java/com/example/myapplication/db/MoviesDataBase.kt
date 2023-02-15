package com.example.myapplication.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MovieEntity::class], version = 1, exportSchema = true)
abstract class MoviesDataBase : RoomDatabase(){
    abstract fun movieDao():MoviesDao
}