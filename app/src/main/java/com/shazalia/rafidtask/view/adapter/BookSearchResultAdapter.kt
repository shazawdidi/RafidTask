package com.shazalia.rafidtask.view.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.shazalia.rafidtask.databinding.ActivityBookSearchResultBinding
import com.shazalia.rafidtask.model.BookSearchResultData
import com.shazalia.rafidtask.view.BookWishlistRecyclerView

/**
 * @Author: shazawdidi
 * @Date: 6/3/2023
 */
class BookSearchResultAdapter(private val data: ArrayList<BookSearchResultData>,    private val onClick: (BookSearchResultData) -> Unit
) :
    RecyclerView.Adapter<BookSearchResultAdapter.BookSearchResultViewHolder>() {

    inner class BookSearchResultViewHolder(private val item: ActivityBookSearchResultBinding) :
        RecyclerView.ViewHolder(item.root) {
        fun bindTitle(bookSearchResultData: BookSearchResultData) {
            // Attach data to item
            item.bookNameTextView.text = bookSearchResultData.title
            item.publisherNameTextView.text = bookSearchResultData.publisher
             item.bookThumbnail.load(bookSearchResultData.bookThumbnail)
            item.searchResult.apply {
                setOnClickListener {
                    onClick(bookSearchResultData)
                }
            }

        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookSearchResultViewHolder {
        return BookSearchResultViewHolder(
            ActivityBookSearchResultBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: BookSearchResultViewHolder, position: Int) {
        holder.bindTitle(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

}