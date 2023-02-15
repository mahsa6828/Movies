package com.example.myapplication.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myapplication.utils.MOVIES_TABLE

@Entity(tableName = "${MOVIES_TABLE}")
data class MovieEntity(
    @PrimaryKey
    var id:Int = 0,
    var poster:String = "",
    var title:String = "",
    var rate:String="",
    var country:String = "",
    var year:String = ""
)
