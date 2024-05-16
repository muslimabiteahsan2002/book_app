package com.muslima.myapp.repositoty

import android.app.Application
import com.muslima.myapp.db.Book
import com.muslima.myapp.db.BookDB

class BookRepository(application: Application) {
    private val db = BookDB.ins(application)
    private val dao = db.bookDao()
    val showAllBook = dao.showAllBook()
    fun insertBook(book: Book) {
        dao.createBook(book)
    }

    fun editBookData(book: Book) {
        dao.editBookData(book)
    }

    fun removeBookData(id: Int) {
        dao.deleteBookData(id)
    }

    fun deleteAllData(bookAllItem: List<Book>) {
        dao.deleteAllData(bookAllItem)
    }
}