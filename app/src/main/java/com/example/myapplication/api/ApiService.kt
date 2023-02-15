package com.example.myapplication.api

import com.example.myapplication.model.detail.ResponseDetailMovies
import com.example.myapplication.model.home.ResponseGenres
import com.example.myapplication.model.home.ResponseMoviesList
import com.example.myapplication.model.register.BodyRegister
import com.example.myapplication.model.register.ResponseRegister
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @POST("register")
    suspend fun registerUser(@Body body: BodyRegister):Response<ResponseRegister>

    @GET("genres/{genre_id}/movies")
    suspend fun topMovies(@Path("genre_id") id:Int):Response<ResponseMoviesList>

    @GET("genres")
    suspend fun genresList():Response<ResponseGenres>

    @GET("movies")
    suspend fun lastMovies():Response<ResponseMoviesList>

    @GET("movies")
    suspend fun searchMovies(@Query("q") name:String):Response<ResponseMoviesList>

    @GET("movies/{movie_id}")
    suspend fun detailMovies(@Path("movie_id") id:Int):Response<ResponseDetailMovies>
}