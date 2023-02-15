package com.example.myapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.home.ResponseGenres
import com.example.myapplication.model.home.ResponseMoviesList
import com.example.myapplication.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: HomeRepository):ViewModel(){
    val topMovies = MutableLiveData<ResponseMoviesList>()
    val genresList = MutableLiveData<ResponseGenres>()
    val lastMovies = MutableLiveData<ResponseMoviesList>()
    val loading = MutableLiveData<Boolean>()

    fun callApis(id:Int) = viewModelScope.launch {
        loading.postValue(true)
        val topMovie1 = async {
            repository.topMovies(id)
        }
        val genreList1 = async {
            repository.genresList()
        }
        val lastMovies1 = async {
            repository.lastMovies()
        }
        topMovies.postValue(topMovie1.await().body())
        genresList.postValue(genreList1.await().body())
        lastMovies.postValue(lastMovies1.await().body())
        loading.postValue(false)
    }

//    fun topMovies(id:Int) = viewModelScope.launch {
//        val response = repository.topMovies(id)
//        if (response.isSuccessful){
//            topMovies.postValue(response.body())
//        }
//    }
//
//    fun genresList() = viewModelScope.launch {
//        val response = repository.genresList()
//        if (response.isSuccessful){
//            genresList.postValue(response.body())
//        }
//    }
//
//    fun lastMovies() = viewModelScope.launch {
//        loading.postValue(true)
//        val response = repository.lastMovies()
//        if (response.isSuccessful){
//            lastMovies.postValue(response.body())
//        }
//        loading.postValue(false)
//    }



}