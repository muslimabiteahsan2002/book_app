package com.muslima.myapp.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.muslima.myapp.R
import com.muslima.myapp.databinding.ActivityAddDataBinding
import com.muslima.myapp.db.Book
import com.muslima.myapp.view_model.BookViewModel

class AddDataActivity : AppCompatActivity() {
    private val binding: ActivityAddDataBinding by lazy {
        ActivityAddDataBinding.inflate(layoutInflater)
    }
    private lateinit var viewModel: BookViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[BookViewModel::class.java]
        val idData = intent.getIntExtra(idKey, 0)
        val titleData = intent.getStringExtra(titleKey)
        val authorData = intent.getStringExtra(authorKey)
        val pageData = intent.getIntExtra(pageKey, 0)
        if (intent.hasExtra(idKey)) {
            editBookData(idData, titleData, authorData, pageData)
        } else {
            addBookData()
        }
    }
    private fun setToolbar(toolbarTitle:String) {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val toolbarText = findViewById<TextView>(R.id.toolbarText)
        val backButton = findViewById<ImageButton>(R.id.backBtn)
        val deleteButton = findViewById<ImageButton>(R.id.delAllDataBtn)
        toolbarText.text=toolbarTitle
        deleteButton.visibility = View.GONE
        backButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
    @SuppressLint("SetTextI18n")
    private fun editBookData(idData: Int, titleData: String?, authorData: String?, pageData: Int) {
        setToolbar("Update Data")
        binding.saveBtn.text = "edit data"
        binding.titleEdt.setText(titleData)
        binding.authorEdt.setText(authorData)
        binding.pageEdt.setText("$pageData")
        binding.saveBtn.setOnClickListener {
            val bookTitle = binding.titleEdt.text.toString()
            val bookPage = binding.pageEdt.text.toString()
            val bookAuthor = binding.authorEdt.text.toString()
            val bookData = Book(
                title = bookTitle,
                author = bookAuthor,
                page = bookPage.toInt(),
                id = idData
            )
            viewModel.editBookData(bookData)
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun addBookData() {
        setToolbar("Add Data")
        binding.saveBtn.setOnClickListener {
            val bookTitle = binding.titleEdt.text.toString()
            val bookPage = binding.pageEdt.text.toString()
            val bookAuthor = binding.authorEdt.text.toString()
            if (bookTitle.isEmpty() || bookPage.isEmpty() || bookAuthor.isEmpty()) {
                Toast.makeText(this, "Please, enter the value", Toast.LENGTH_SHORT).show()
            } else {
                val bookData = Book(
                    title = bookTitle,
                    author = bookAuthor,
                    page = bookPage.toInt()
                )
                viewModel.insertBook(bookData)
                startActivity(Intent(this, MainActivity::class.java))
            }
        }
    }

    companion object {
        const val idKey = "id"
        const val titleKey = "title"
        const val authorKey = "author"
        const val pageKey = "page"
    }
}