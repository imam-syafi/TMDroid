package com.edts.tmdroid.ui.movie.detail

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import com.edts.tmdroid.R
import com.edts.tmdroid.data.local.AppDatabase
import com.edts.tmdroid.data.local.entity.FavoriteMovieDao
import com.edts.tmdroid.databinding.ActivityMovieDetailBinding
import com.edts.tmdroid.ext.loadFromUrl
import com.edts.tmdroid.ui.model.Movie
import com.edts.tmdroid.ui.movie.favorite.MovieFavoriteActivity
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeUtils
import com.google.android.material.badge.ExperimentalBadgeUtils
import kotlinx.coroutines.launch

class MovieDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailBinding

    private lateinit var toolbar: Toolbar
    private lateinit var badge: BadgeDrawable

    private lateinit var favoriteMovieDao: FavoriteMovieDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val title = intent.getStringExtra(PAGE_TITLE)
        supportActionBar?.let {
            it.title = title
            it.setDisplayHomeAsUpEnabled(true)
        }

        toolbar = findViewById(androidx.appcompat.R.id.action_bar)
        badge = BadgeDrawable.create(this).apply {
            horizontalOffset = 20
            verticalOffset = 8
        }

        favoriteMovieDao = AppDatabase
            .getInstance(this@MovieDetailActivity)
            .favoriteMovieDao()

        favoriteMovieDao
            .count()
            .observe(this) {
                badge.number = it
            }

        intent.getParcelableExtra<Movie>(DETAIL_INFO)?.let { movie ->
            binding.render(movie)
        }
    }

    private fun ActivityMovieDetailBinding.render(movie: Movie) {
        backdrop.loadFromUrl(movie.backdropUrl)
        poster.loadFromUrl(movie.posterUrl)
        title.text = movie.title
        releaseDate.text = movie.releaseDate
        rating.text = getString(R.string.rating, movie.voteAverage.toString(), movie.voteCount)
        overview.text = movie.overview

        favoriteMovieDao
            .isSaved(movie.id)
            .observe(this@MovieDetailActivity) { isMovieSaved ->
                with(fabFavorite) {
                    if (isMovieSaved) {
                        setText(R.string.remove_from_favorite)
                        setIconResource(R.drawable.ic_favorite_filled)
                    } else {
                        setText(R.string.add_to_favorite)
                        setIconResource(R.drawable.ic_favorite)
                    }

                    setOnClickListener {
                        val entity = movie.toEntity()

                        lifecycleScope.launch {
                            if (isMovieSaved) {
                                favoriteMovieDao.delete(entity)
                            } else {
                                favoriteMovieDao.save(entity)
                            }
                        }
                    }
                }
            }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_fave, menu)
        return super.onCreateOptionsMenu(menu)
    }

    @ExperimentalBadgeUtils
    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        BadgeUtils.attachBadgeDrawable(badge, toolbar, R.id.action_fave)
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_fave -> {
                MovieFavoriteActivity.open(
                    this,
                    getString(R.string.favorite_movies),
                )
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    companion object {
        const val PAGE_TITLE = "page_title"
        const val DETAIL_INFO = "detail_info"

        fun open(activity: AppCompatActivity, title: String, movie: Movie) {
            val intent = Intent(activity, MovieDetailActivity::class.java).apply {
                putExtra(PAGE_TITLE, title)
                putExtra(DETAIL_INFO, movie)
            }

            ActivityCompat.startActivity(activity, intent, null)
        }
    }
}
