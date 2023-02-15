package com.example.myapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.db.MovieEntity
import com.example.myapplication.model.detail.ResponseDetailMovies
import com.example.myapplication.repository.DetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val repository: DetailRepository) : ViewModel() {
    val detailsMovie = MutableLiveData<ResponseDetailMovies>()
    val loading = MutableLiveData<Boolean>()

    fun detailsMovie(id:Int) = viewModelScope.launch {
        loading.postValue(true)
        val response = repository.detailsMovie(id)
        if (response.isSuccessful){
            detailsMovie.postValue(response.body())
        }
        loading.postValue(false)
    }

    //database
    val isFavorite = MutableLiveData<Boolean>()

    fun isExists(id:Int) = viewModelScope.launch {
        val exists = repository.existMovie(id)
        if (exists){
            isFavorite.postValue(true)
        }
        else
        {
            isFavorite.postValue(false)
        }
    }

    fun favoriteMovie(id:Int,entity: MovieEntity) = viewModelScope.launch {
        val exists = repository.existMovie(id)
        if (exists){
            isFavorite.postValue(false)
            repository.deleteMovie(entity)
        }
        else
        {
            isFavorite.postValue(true)
            repository.insertMovie(entity)
        }
    }

}