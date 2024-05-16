package com.muslima.myapp.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.muslima.myapp.R
import com.muslima.myapp.databinding.ActivitySingleDataBinding

class SingleDataActivity : AppCompatActivity() {
    private val binding: ActivitySingleDataBinding by lazy {
        ActivitySingleDataBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val titleData = intent.getStringExtra(AddDataActivity.titleKey)
        val authorData = intent.getStringExtra(AddDataActivity.authorKey)
        val pageData = intent.getIntExtra(AddDataActivity.pageKey, 0)
        binding.title.text = titleData
        binding.author.text = authorData
        binding.page.text = "$pageData"
        setToolbar()
    }

    @SuppressLint("SetTextI18n")
    private fun setToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val toolbarText = findViewById<TextView>(R.id.toolbarText)
        val backButton = findViewById<ImageButton>(R.id.backBtn)
        val deleteButton = findViewById<ImageButton>(R.id.delAllDataBtn)
        toolbarText.text = "Show Single Data"
        deleteButton.visibility = View.GONE
        backButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}