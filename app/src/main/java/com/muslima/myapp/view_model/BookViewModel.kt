package com.muslima.myapp.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.muslima.myapp.db.Book
import com.muslima.myapp.repositoty.BookRepository


class BookViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = BookRepository(application)
    val showAllBook = repository.showAllBook
    fun insertBook(book: Book) {
        repository.insertBook(book)
    }

    fun editBookData(book: Book) {
        repository.editBookData(book)
    }

    fun removeBookData(id: Int) {
        repository.removeBookData(id)
    }

    fun deleteAllData(bookAllItem: List<Book>) {
        repository.deleteAllData(bookAllItem)
    }
}
