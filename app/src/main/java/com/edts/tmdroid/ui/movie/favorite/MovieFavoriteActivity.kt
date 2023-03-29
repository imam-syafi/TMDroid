package com.edts.tmdroid.ui.movie.favorite

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import com.edts.tmdroid.data.local.AppDatabase
import com.edts.tmdroid.databinding.ActivityMovieFavoriteBinding
import com.edts.tmdroid.ui.model.Movie
import com.edts.tmdroid.ui.movie.list.MovieListAdapter

class MovieFavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieFavoriteBinding
    private lateinit var movieListAdapter: MovieListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMovieFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val title = intent.getStringExtra(PAGE_TITLE)
        supportActionBar?.let {
            it.title = title
            it.setDisplayHomeAsUpEnabled(true)
        }

        binding.setup()

        AppDatabase
            .getInstance(this)
            .favoriteMovieDao()
            .getAll()
            .observe(this) { entities ->
                val movieList = entities.map(Movie::from)
                binding.render(movieList)
            }
    }

    private fun ActivityMovieFavoriteBinding.setup() {
        movieListAdapter = MovieListAdapter(
            onClick = {
                // TODO: Navigate to detail screen
            },
        ).also(rvMovies::setAdapter)
    }

    private fun ActivityMovieFavoriteBinding.render(movieList: List<Movie>) {
        tvEmpty.isVisible = movieList.isEmpty()
        movieListAdapter.submitList(movieList)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    companion object {
        const val PAGE_TITLE = "page_title"

        fun open(activity: AppCompatActivity, title: String) {
            val intent = Intent(activity, MovieFavoriteActivity::class.java).apply {
                putExtra(PAGE_TITLE, title)
            }

            ActivityCompat.startActivity(activity, intent, null)
        }
    }
}
