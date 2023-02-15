package com.example.myapplication.db

import androidx.room.*
import com.example.myapplication.utils.MOVIES_TABLE

@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movieEntity: MovieEntity)

    @Delete
    suspend fun delete(movieEntity: MovieEntity)

    @Query("SELECT * FROM ${MOVIES_TABLE}")
    fun getAllMovies() : MutableList<MovieEntity>

    @Query("SELECT EXISTS (SELECT * FROM ${MOVIES_TABLE} WHERE id = :id)")
    suspend fun existMovie(id:Int):Boolean
}