package com.example.myapplication.repository

import com.example.myapplication.api.ApiService
import com.example.myapplication.model.register.BodyRegister
import javax.inject.Inject

class RegisterRepository @Inject constructor(private val api:ApiService) {
    suspend fun registerUser(body:BodyRegister) = api.registerUser(body)

}