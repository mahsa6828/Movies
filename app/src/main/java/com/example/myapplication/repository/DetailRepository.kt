package com.example.myapplication.repository

import com.example.myapplication.api.ApiService
import com.example.myapplication.db.MovieEntity
import com.example.myapplication.db.MoviesDao
import javax.inject.Inject

class DetailRepository @Inject constructor(private val api:ApiService,private val dao:MoviesDao) {
    //api
    suspend fun detailsMovie(id:Int) = api.detailMovies(id)

    //database
    suspend fun insertMovie(entity: MovieEntity) = dao.insert(entity)
    suspend fun deleteMovie(entity: MovieEntity) = dao.delete(entity)
    suspend fun existMovie(id:Int) = dao.existMovie(id)

}