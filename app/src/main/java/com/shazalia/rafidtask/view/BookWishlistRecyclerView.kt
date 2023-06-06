package com.shazalia.rafidtask.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.shazalia.rafidtask.R
import com.shazalia.rafidtask.databinding.ActivityBookWishlistRecyclerViewBinding
import com.shazalia.rafidtask.model.BookSearchResultData
import com.shazalia.rafidtask.view.adapter.WishlistAdapter
import com.shazalia.rafidtask.viewmodel.BookDataViewModel

class BookWishlistRecyclerView : AppCompatActivity(), WishlistAdapter.DeleteBookInterface {

    private lateinit var binding: ActivityBookWishlistRecyclerViewBinding
    private lateinit var mBookDataViewModel: BookDataViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookWishlistRecyclerViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Wishlist"

        // Recyclerview
        val wishlistAdapter = WishlistAdapter(this)
        binding.bookWishListRecyclerView.adapter = wishlistAdapter
        binding.bookWishListRecyclerView.layoutManager = LinearLayoutManager(this)

        // BookDataViewModel
        mBookDataViewModel = ViewModelProvider(this).get(BookDataViewModel::class.java)
        mBookDataViewModel.readAllData.observe(this, Observer { book ->
            wishlistAdapter.setData(book)
        })
    }

    override fun onClick(book: BookSearchResultData) {
        mBookDataViewModel.deleteBook(book)
        Toast.makeText(applicationContext, "Book removed from wishlist", Toast.LENGTH_LONG)
            .show()
    }
}