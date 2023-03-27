package com.edts.tmdroid.ui.movie.list

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.edts.tmdroid.R
import com.edts.tmdroid.data.remote.NetworkModule
import com.edts.tmdroid.databinding.ActivityMovieListBinding
import com.edts.tmdroid.ui.model.Movie
import com.edts.tmdroid.ui.movie.detail.MovieDetailActivity
import kotlinx.coroutines.launch

class MovieListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieListBinding
    private val movieListAdapter = MovieListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMovieListBinding.inflate(layoutInflater)
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

    private fun ActivityMovieListBinding.setup() {
        rvMovies.adapter = movieListAdapter.apply {
            delegate = object : MovieDelegate {
                override fun onMovieClicked(movie: Movie) {
                    MovieDetailActivity.open(
                        this@MovieListActivity,
                        getString(R.string.movie_detail),
                        movie,
                    )
                }
            }
        }
    }

    private fun ActivityMovieListBinding.render(query: String?) {
        lifecycleScope.launch {
            try {
                val response = if (query != null) {
                    NetworkModule.tmdbService.searchMovies(query)
                } else {
                    NetworkModule.tmdbService.getTopRatedMovies()
                }

                val movieList = response.results.map(Movie::from)

                rvMovies.isVisible = movieList.isNotEmpty()
                err.isVisible = movieList.isEmpty()

                movieListAdapter.submitList(movieList)
            } catch (t: Throwable) {
                handleError(t.message)
            } finally {
                stopLoading()
            }
        }
    }

    private fun ActivityMovieListBinding.handleError(_message: CharSequence? = null) {
        val message = _message
            ?.trim()
            ?.ifBlank { null } ?: getString(R.string.default_error_message)

        rvMovies.isVisible = false
        err.isVisible = true

        err.text = message
    }

    private fun ActivityMovieListBinding.stopLoading() {
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
            val intent = Intent(activity, MovieListActivity::class.java).apply {
                putExtra(PAGE_TITLE, title)
                putExtra(SEARCH_QUERY, query)
            }

            ActivityCompat.startActivity(activity, intent, null)
        }
    }
}
