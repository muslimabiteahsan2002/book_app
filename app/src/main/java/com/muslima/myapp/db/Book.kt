package com.muslima.myapp.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Book(
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,
    val title:String="",
    val author:String="",
    val page:Int=0
)
