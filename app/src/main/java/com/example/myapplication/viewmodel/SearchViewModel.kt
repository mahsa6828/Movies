package com.example.myapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.home.ResponseMoviesList
import com.example.myapplication.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: SearchRepository) : ViewModel(){
    val searchMovies = MutableLiveData<ResponseMoviesList>()
    val emptyList = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun searchMovies(name:String) = viewModelScope.launch {
        loading.postValue(true)
        var response = repository.searchMovies(name)
        if (response.isSuccessful){
            if (response.body()?.data!!.isNotEmpty()){
                searchMovies.postValue(response.body())
                emptyList.postValue(false)
            }
            else{
                emptyList.postValue(true)
            }
        }
        loading.postValue(false)
        }
    }

