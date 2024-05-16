package com.muslima.myapp.ui

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Canvas
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.muslima.myapp.R
import com.muslima.myapp.adapter.BookAdapter
import com.muslima.myapp.databinding.ActivityMainBinding
import com.muslima.myapp.db.Book
import com.muslima.myapp.view_model.BookViewModel
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var adapter: BookAdapter
    private lateinit var viewModel: BookViewModel
    private var bookItemList = ArrayList<Book>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[BookViewModel::class.java]
        binding.addBtn.setOnClickListener {
            startActivity(Intent(this, AddDataActivity::class.java))
        }
        viewModel.showAllBook.observe(this) {
            bookItemList = it as ArrayList<Book>
            setData(bookItemList)
        }
        setRecycle()
        setCustomToolbar()
    }

    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    private fun setCustomToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val toolbarText = findViewById<TextView>(R.id.toolbarText)
        val backButton = findViewById<ImageButton>(R.id.backBtn)
        val deleteButton = findViewById<ImageButton>(R.id.delAllDataBtn)
        toolbarText.text = "My Book Library App"
        backButton.visibility = View.GONE
        deleteButton.setOnClickListener {
            val builder: AlertDialog.Builder = AlertDialog.Builder(this@MainActivity)
            builder.setMessage("Are you sure?")
            builder.setTitle("Do you want to delete all data?")
            builder.setCancelable(false)
            builder.setNegativeButton(
                "No"
            ) { dialog, _ ->
                dialog.cancel()
            }
            builder.setPositiveButton(
                "Yes"
            ) { _, _ ->
                viewModel.deleteAllData(bookItemList)
                adapter.notifyDataSetChanged()
            }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.show()
        }
    }

    private fun setRecycle() {
        val callback: ItemTouchHelper.SimpleCallback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val p = viewHolder.adapterPosition
                val bookItem = bookItemList[p]
                when (direction) {
                    ItemTouchHelper.LEFT -> {
                        bookItemList.removeAt(p)
                        val id = bookItem.id
                        viewModel.removeBookData(id)
                        adapter.notifyDataSetChanged()
                    }

                    ItemTouchHelper.RIGHT -> {
                        val intentData = Intent(applicationContext, AddDataActivity::class.java)
                        intentData.putExtra(AddDataActivity.idKey, bookItem.id)
                        intentData.putExtra(AddDataActivity.titleKey, bookItem.title)
                        intentData.putExtra(AddDataActivity.authorKey, bookItem.author)
                        intentData.putExtra(AddDataActivity.pageKey, bookItem.page)
                        startActivity(intentData)
                        adapter.notifyDataSetChanged()
                    }
                }
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                RecyclerViewSwipeDecorator.Builder(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
                    .addSwipeLeftBackgroundColor(
                        ContextCompat.getColor(
                            applicationContext,
                            R.color.deleteColor
                        )
                    )
                    .addSwipeLeftActionIcon(R.drawable.baseline_delete_24)
                    .addSwipeRightBackgroundColor(
                        ContextCompat.getColor(
                            applicationContext,
                            R.color.archiveColor
                        )
                    )
                    .addSwipeRightActionIcon(R.drawable.baseline_edit_24)
                    .create()
                    .decorate()
                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(binding.show)
    }

    private fun setData(bookItemList: ArrayList<Book>) {
        adapter = BookAdapter(bookItemList, this@MainActivity)
        binding.show.layoutManager = LinearLayoutManager(this@MainActivity)
        binding.show.adapter = adapter
    }
}