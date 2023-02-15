package com.example.myapplication.model.home

class ResponseGenres : ArrayList<ResponseGenres.ResponseGenresItem>(){
    data class ResponseGenresItem(
        val id: Int,
        val name: String
    )
}