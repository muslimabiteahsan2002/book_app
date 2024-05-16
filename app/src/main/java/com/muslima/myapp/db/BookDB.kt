package com.muslima.myapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Book::class], version = 1)
abstract class BookDB:RoomDatabase() {
    abstract fun bookDao(): BookDao
    companion object{
        fun ins(context: Context):BookDB{
            val db = Room.databaseBuilder(
                context,
                BookDB::class.java, "database-name"
            ).allowMainThreadQueries().build()
            return db
        }
    }
}