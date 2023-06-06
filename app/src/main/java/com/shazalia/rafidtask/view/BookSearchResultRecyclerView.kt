package com.shazalia.rafidtask.view

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.shazalia.rafidtask.BuildConfig
import com.shazalia.rafidtask.model.BookSearchResultData
import com.shazalia.rafidtask.databinding.ActivityBookSearchResultRecyclerViewBinding
import com.shazalia.rafidtask.repository.Repository
import com.shazalia.rafidtask.view.adapter.BookSearchResultAdapter
import com.shazalia.rafidtask.viewmodel.MainViewModel
import com.shazalia.rafidtask.viewmodel.MainViewModelFactory
import java.util.*
import kotlin.collections.ArrayList

class BookSearchResultRecyclerView : AppCompatActivity() {

    private lateinit var binding: ActivityBookSearchResultRecyclerViewBinding
    private lateinit var viewModel: MainViewModel
    val repository = Repository()

    val viewModelFactory = MainViewModelFactory(repository)

    private var pass: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        binding = ActivityBookSearchResultRecyclerViewBinding.inflate(layoutInflater)

        setContentView(binding.root)
        binding.wishlistButton.setOnClickListener {
            val intent = Intent(this, BookWishlistRecyclerView::class.java)
            startActivity(intent)
        }

        val bookName: ArrayList<String> =
            intent.getStringArrayListExtra("bookName") as ArrayList<String>

        val bookPublisher: ArrayList<String> =
            intent.getStringArrayListExtra("publisher") as ArrayList<String>
        val bookThumbnail: ArrayList<String> =
            intent.getStringArrayListExtra("bookThumbnail") as ArrayList<String>
        val id = java.util.ArrayList<String>()

        val bookSmallThumbnail = java.util.ArrayList<String>()

         val bookDescription = java.util.ArrayList<String>()
        val previewLink = java.util.ArrayList<String>()
        val data = arrayListOf<BookSearchResultData>()
        repeat(bookName.size) {
            data.add(
                BookSearchResultData(
                    bookThumbnail[pass],
                    bookName[pass],
                    bookPublisher[pass],"","","",bookThumbnail[pass]
                )
            )
            pass += 1
        }

        val bookSearchResultAdapter = BookSearchResultAdapter(data) {
            val intent = Intent(this, BookDetailsActivity::class.java)
            intent.putExtra("id", it.id)
            intent.putExtra("bookName", it.title)
            intent.putExtra("bookPublisher", it.publisher)
            intent.putExtra("bookDescription", it.bookDescription)
            intent.putExtra("previewLink", it.previewLink)
            intent.putExtra("bookThumbnail", it.bookThumbnail)
            startActivity(intent)
        }
        binding.bookSearchResultRecyclerView.adapter = bookSearchResultAdapter
        binding.bookSearchResultRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.icSearch.setOnClickListener {
            if (checkInternet(this)) {
                val title: String = binding.searchBookTextInput.editText?.text.toString()
                if (title.isNotEmpty()) {
                    Toast.makeText(
                        applicationContext,
                        "Searching books having \"${
                            title.replaceFirstChar {
                                if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
                            }
                        }\" in name.",
                        Toast.LENGTH_LONG
                    ).show()

                viewModel.getBooks("android kotlin", BuildConfig.API_KEY)
                viewModel.myResponse.observe(this) { response ->
                    if (response.isSuccessful) {
                        response.body()!!.items.forEach {
                            id.add(it.id.toString())
                            bookName.add(it.volumeInfo!!.title.toString())
                            bookPublisher.add(it.volumeInfo!!.publisher.toString())
                            bookSmallThumbnail.add(it.volumeInfo!!.imageLinks!!.smallThumbnail.toString())
                            bookThumbnail.add(it.volumeInfo!!.imageLinks!!.thumbnail.toString())
                            bookDescription.add(it.volumeInfo!!.description.toString())
                            previewLink.add(it.volumeInfo!!.previewLink.toString())
                        }
                        val intent = Intent(this, BookSearchResultRecyclerView::class.java)
                        intent.putExtra("id", id)
                        intent.putExtra("bookName", bookName)
                        intent.putExtra("publisher", bookPublisher)
                        intent.putExtra("bookSmallThumbnail", bookSmallThumbnail)
                        intent.putExtra("bookThumbnail", bookThumbnail)
                        intent.putExtra("bookDescription", bookDescription)
                        intent.putExtra("previewLink", previewLink)

                        startActivity(intent)
                        finish()
                    } else {
                        val intent = Intent(this, ServerErrorActivity::class.java)
                        startActivity(intent)
                    }
                }
//                } else {
//                    Toast.makeText(applicationContext, "Title can not be empty", Toast.LENGTH_LONG)
//                        .show()
//                }
            } else {
                Toast.makeText(applicationContext, "Please connect to internet", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    }
    }

    private fun checkInternet(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        // if the android version is equal to M
        // or greater we need to use the
        // NetworkCapabilities to check what type of
        // network has the internet connection
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // Returns a Network object corresponding to
            // the currently active default data network.
            val network = connectivityManager.activeNetwork ?: return false

            // Representation of the capabilities of an active network.
            val activeNetwork =
                connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                // Indicates this network uses a Wi-Fi transport,
                // or WiFi has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

                // Indicates this network uses a Cellular transport. or
                // Cellular has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

                // else return false
                else -> false
            }
        } else {
            // if the android version is below M
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }

    override fun onBackPressed() {
        super.onDestroy()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

}