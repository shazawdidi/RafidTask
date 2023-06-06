package com.shazalia.rafidtask.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shazalia.rafidtask.R
import com.shazalia.rafidtask.model.BookSearchResultData


/**
 * @Author: shazawdidi
 * @Date: 6/3/2023
 */

class WishlistAdapter(private val listener: DeleteBookInterface) :
    RecyclerView.Adapter<WishlistAdapter.MyViewHolder>() {

    private var bookList = emptyList<BookSearchResultData>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.book_wishlist_row, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = bookList[position]
         holder.itemView.findViewById<TextView>(R.id.book_name).text= currentItem.title
         holder.itemView.findViewById<TextView>(R.id.book_publisher).text= currentItem.publisher
         holder.itemView.findViewById<ImageView>(R.id.delete_image_view).setOnClickListener {
             listener.onClick(bookList[holder.adapterPosition])
         }

    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    fun setData(book: List<BookSearchResultData>) {
        this.bookList = book
        notifyDataSetChanged()
    }

    interface DeleteBookInterface {
        fun onClick(book: BookSearchResultData)
    }
}