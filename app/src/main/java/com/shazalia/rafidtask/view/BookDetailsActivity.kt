package com.shazalia.rafidtask.view

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.shazalia.rafidtask.model.BookSearchResultData
import com.shazalia.rafidtask.R
import com.shazalia.rafidtask.databinding.ActivityBookDetailsBinding
import com.shazalia.rafidtask.viewmodel.BookDataViewModel

class BookDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookDetailsBinding
    private lateinit var mBookDataViewModel: BookDataViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Book Details"

        val id: String = intent.getStringExtra("id").toString()
        val bookThumbnail: String = intent.getStringExtra("bookThumbnail").toString()
        val bookName: String = intent.getStringExtra("bookName").toString()
        val bookPublisher: String = intent.getStringExtra("bookPublisher").toString()
        val bookDescription: String = intent.getStringExtra("bookDescription").toString()
        val previewLink: String = intent.getStringExtra("previewLink").toString()
        val isFavourite = false

        binding.bookName.text = bookName
        binding.publisherNameTextView.text = bookPublisher
        binding.bookDescription.text = bookDescription
        binding.bookThumbnail.load(bookThumbnail)

        mBookDataViewModel = ViewModelProvider(this).get(BookDataViewModel::class.java)

        binding.previewButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(previewLink)
            startActivity(intent)
        }

        val book = BookSearchResultData(
            id,
            bookThumbnail,
            bookName,
            bookPublisher,
            bookDescription,
            previewLink,
            bookThumbnail,
            isFavourite
        )

        binding.wishlistImageView.setOnClickListener {

            if (book.isFavourite == true) {

                binding.wishlistImageView.setImageResource(R.drawable.ic_empty_star)
                mBookDataViewModel.deleteBook(book)
                book.isFavourite = false

                Toast.makeText(applicationContext, "Book removed from wishlist", Toast.LENGTH_LONG)
                    .show()
            } else {

                binding.wishlistImageView.setImageResource(R.drawable.ic_filled_star)
                mBookDataViewModel.addBook(book)
                book.isFavourite = true

                Toast.makeText(applicationContext, "Book added to wishlist", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }
}