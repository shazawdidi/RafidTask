package com.shazalia.rafidtask.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.shazalia.rafidtask.BuildConfig
import com.shazalia.rafidtask.databinding.ActivityBookSearchResultRecyclerViewBinding
import com.shazalia.rafidtask.databinding.ActivityMainBinding
import com.shazalia.rafidtask.model.BookSearchResultData
import com.shazalia.rafidtask.repository.Repository
import com.shazalia.rafidtask.utils.allQuotesList
import com.shazalia.rafidtask.view.adapter.BookSearchResultAdapter
import com.shazalia.rafidtask.viewmodel.MainViewModel
import com.shazalia.rafidtask.viewmodel.MainViewModelFactory
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        val API_KEY: String = getenv("API_KEY")!!.toString()
        val id = ArrayList<String>()
        val bookName = ArrayList<String>()
        val bookPublisher = ArrayList<String>()
        val bookSmallThumbnail = ArrayList<String>()

        val bookThumbnail = ArrayList<String>()
        val bookDescription = ArrayList<String>()
        val previewLink = ArrayList<String>()

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        binding = ActivityMainBinding.inflate(layoutInflater)

//        binding.quote.text = allQuotesList.random()
//        binding.searchButton.setOnClickListener {
            if (checkInternet(this)) {
//                val title: String = binding.searchBookTextInput.editText?.text.toString()
//                if (title.isNotEmpty()) {
//                    Toast.makeText(
//                        applicationContext,
//                        "Searching books having \"${
//                            title.replaceFirstChar {
//                                if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
//                            }
//                        }\" in name.",
//                        Toast.LENGTH_LONG
//                    ).show()

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
//        }

//        binding.wishlistButton.setOnClickListener {
//            val intent = Intent(this, BookWishlistRecyclerView::class.java)
//            startActivity(intent)
//        }
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
}