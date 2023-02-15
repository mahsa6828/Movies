package com.example.myapplication.model.register

import com.google.gson.annotations.SerializedName

data class ResponseRegister(
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String,
    @SerializedName("updated_at")
    val updatedAt: String?
)
