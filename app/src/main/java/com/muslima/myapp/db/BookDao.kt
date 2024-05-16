package com.muslima.myapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface BookDao {
    @Insert
    fun createBook(book: Book)

    @Query("select * from Book")
    fun showAllBook(): LiveData<List<Book>>

    @Update
    fun editBookData(book: Book)

    @Query("delete from Book where id=:id")
    fun deleteBookData(id: Int)

    @Delete
    fun deleteAllData(bookAllItem: List<Book>)
}