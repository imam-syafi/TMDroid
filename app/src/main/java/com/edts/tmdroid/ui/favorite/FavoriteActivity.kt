package com.edts.tmdroid.ui.favorite

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import com.edts.tmdroid.R
import com.edts.tmdroid.data.Movie
import com.edts.tmdroid.data.local.AppDatabase
import com.edts.tmdroid.databinding.ActivityFavoriteBinding
import com.edts.tmdroid.ui.detail.DetailActivity
import com.edts.tmdroid.ui.list.MovieDelegate
import com.edts.tmdroid.ui.list.MovieListAdapter

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private val movieListAdapter = MovieListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFavoriteBinding.inflate(layoutInflater)
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

    private fun ActivityFavoriteBinding.setup() {
        rvMovies.adapter = movieListAdapter.apply {
            delegate = object : MovieDelegate {
                override fun onMovieClicked(movie: Movie) {
                    DetailActivity.open(
                        this@FavoriteActivity,
                        getString(R.string.movie_detail),
                        movie
                    )
                }
            }
        }
    }

    private fun ActivityFavoriteBinding.render(movieList: List<Movie>) {
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
            val intent = Intent(activity, FavoriteActivity::class.java).apply {
                putExtra(PAGE_TITLE, title)
            }

            ActivityCompat.startActivity(activity, intent, null)
        }
    }
}
