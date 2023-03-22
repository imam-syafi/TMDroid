package com.edts.tmdroid.ui.list

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import com.edts.tmdroid.R
import com.edts.tmdroid.data.Movie
import com.edts.tmdroid.databinding.ActivityListBinding
import com.edts.tmdroid.service.TmdbService
import com.edts.tmdroid.service.response.GetMoviesResponse
import com.edts.tmdroid.ui.detail.DetailActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListBinding
    private val movieAdapter = MovieAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val title = intent.getStringExtra(PAGE_TITLE)
        supportActionBar?.let {
            it.title = title
            it.setDisplayHomeAsUpEnabled(true)
        }

        binding.setup()

        val query = intent.getStringExtra(SEARCH_QUERY)
        binding.render(query)
    }

    private fun ActivityListBinding.setup() {
        rvMovies.adapter = movieAdapter.apply {
            delegate = object : MovieDelegate {
                override fun onMovieClicked(movie: Movie) {
                    DetailActivity.open(
                        this@ListActivity,
                        getString(R.string.movie_detail),
                        movie
                    )
                }
            }
        }
    }

    private fun ActivityListBinding.render(query: String?) {
        val call = if (query != null) {
            TmdbService.instance.searchMovies(query)
        } else {
            TmdbService.instance.getTopRatedMovies()
        }

        call.enqueue(object : Callback<GetMoviesResponse> {
            override fun onResponse(
                call: Call<GetMoviesResponse>,
                response: Response<GetMoviesResponse>
            ) {
                stopLoading()

                if (response.isSuccessful) {
                    val body = response.body()

                    if (body != null) {
                        val movieList = body.results.map(Movie::from)

                        rvMovies.isVisible = movieList.isNotEmpty()
                        err.isVisible = movieList.isEmpty()

                        movieAdapter.setData(movieList)
                    } else {
                        handleError(response.message())
                    }
                } else {
                    handleError(response.message())
                }
            }

            override fun onFailure(call: Call<GetMoviesResponse>, t: Throwable) {
                stopLoading()
                handleError(t.message)
            }
        })
    }

    private fun ActivityListBinding.handleError(_message: CharSequence? = null) {
        val message = _message
            ?.trim()
            ?.ifBlank { null } ?: getString(R.string.default_error_message)

        rvMovies.isVisible = false
        err.isVisible = true

        err.text = message
    }

    private fun ActivityListBinding.stopLoading() {
        flShimmer.stopShimmer()
        svShimmer.isVisible = false
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    companion object {
        const val PAGE_TITLE = "page_title"
        const val SEARCH_QUERY = "search_query"

        fun open(activity: AppCompatActivity, title: String, query: String? = null) {
            val intent = Intent(activity, ListActivity::class.java).apply {
                putExtra(PAGE_TITLE, title)
                putExtra(SEARCH_QUERY, query)
            }

            ActivityCompat.startActivity(activity, intent, null)
        }
    }
}
