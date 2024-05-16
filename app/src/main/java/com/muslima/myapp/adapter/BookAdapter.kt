package com.muslima.myapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.muslima.myapp.databinding.BookItemBinding
import com.muslima.myapp.db.Book
import com.muslima.myapp.ui.AddDataActivity
import com.muslima.myapp.ui.SingleDataActivity

class BookAdapter(private val bookItemList: ArrayList<Book>, private val context: Context) :
    RecyclerView.Adapter<BookAdapter.BookViewHolder>() {
    class BookViewHolder(val binding: BookItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        return BookViewHolder(
            BookItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return bookItemList.size
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val bookItem = bookItemList[position]
        with(holder.binding) {
            titleTv.text = bookItem.title
            authorTv.text = bookItem.author
            pageTv.text = "${bookItem.page}"
        }
        holder.itemView.setOnClickListener {
            val intentData = Intent(context, SingleDataActivity::class.java)
            intentData.putExtra(AddDataActivity.idKey, bookItem.id)
            intentData.putExtra(AddDataActivity.titleKey, bookItem.title)
            intentData.putExtra(AddDataActivity.authorKey, bookItem.author)
            intentData.putExtra(AddDataActivity.pageKey, bookItem.page)
            context.startActivity(intentData)
        }
    }
}
