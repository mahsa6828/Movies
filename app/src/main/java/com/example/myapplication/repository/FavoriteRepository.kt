package com.example.myapplication.repository

import com.example.myapplication.db.MoviesDao
import javax.inject.Inject

class FavoriteRepository @Inject constructor(private val dao: MoviesDao) {
    suspend fun getAllMovies() = dao.getAllMovies()
}