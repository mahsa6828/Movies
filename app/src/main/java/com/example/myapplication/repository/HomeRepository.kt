package com.example.myapplication.repository

import com.example.myapplication.api.ApiService
import javax.inject.Inject

class HomeRepository @Inject constructor(private val api:ApiService) {
    suspend fun topMovies(id:Int) = api.topMovies(id)
    suspend fun genresList() = api.genresList()
    suspend fun lastMovies() = api.lastMovies()

}