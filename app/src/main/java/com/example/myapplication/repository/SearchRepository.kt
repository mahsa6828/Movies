package com.example.myapplication.repository

import com.example.myapplication.api.ApiService
import javax.inject.Inject

class SearchRepository @Inject constructor(private val api:ApiService) {
    suspend fun searchMovies(name:String) = api.searchMovies(name)
}