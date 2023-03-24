package com.edts.tmdroid.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.edts.tmdroid.R
import com.edts.tmdroid.data.Movie
import com.edts.tmdroid.data.local.AppDatabase
import com.edts.tmdroid.data.local.entity.FavoriteMovieDao
import com.edts.tmdroid.databinding.ActivityDetailBinding
import com.edts.tmdroid.ext.loadFromUrl
import com.edts.tmdroid.ui.favorite.FavoriteActivity
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeUtils
import com.google.android.material.badge.ExperimentalBadgeUtils
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var favoriteMovieDao: FavoriteMovieDao
    private var savedCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.setup()

        val title = intent.getStringExtra(PAGE_TITLE)
        supportActionBar?.let {
            it.title = title
            it.setDisplayHomeAsUpEnabled(true)
        }

        favoriteMovieDao = AppDatabase
            .getInstance(this@DetailActivity)
            .favoriteMovieDao()

        favoriteMovieDao
            .count()
            .observe(this) {
                savedCount = it
                invalidateOptionsMenu()
            }

        val movie = intent.getParcelableExtra<Movie>(DETAIL_INFO)
        binding.render(movie)
    }

    private fun ActivityDetailBinding.setup() {
    }

    private fun ActivityDetailBinding.render(movie: Movie?) {
        content.isVisible = movie != null
        err.isVisible = movie == null
        emptyMsg.isVisible = movie == null
        loadRandom.isVisible = movie == null

        if (movie != null) {
            backdrop.loadFromUrl(movie.backdropUrl)
            poster.loadFromUrl(movie.posterUrl)
            title.text = movie.title
            releaseDate.text = movie.releaseDate
            rating.text = getString(R.string.rating, movie.voteAverage.toString(), movie.voteCount)
            overview.text = movie.overview

            favoriteMovieDao
                .isSaved(movie.id)
                .observe(this@DetailActivity) { isMovieSaved ->
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
                                if (isMovieSaved) favoriteMovieDao.delete(entity)
                                else favoriteMovieDao.save(entity)
                            }
                        }
                    }
                }
        }
    }

    @ExperimentalBadgeUtils
    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val toolbar = findViewById<Toolbar>(androidx.appcompat.R.id.action_bar)
        val badge = BadgeDrawable.create(this).apply {
            horizontalOffset = 20
            verticalOffset = 8
            number = savedCount
        }

        BadgeUtils.attachBadgeDrawable(badge, toolbar, R.id.action_fave)

        return super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_fave, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_fave -> {
                FavoriteActivity.open(
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

        fun open(activity: AppCompatActivity, title: String, movie: Movie? = null) {
            val intent = Intent(activity, DetailActivity::class.java).apply {
                putExtra(PAGE_TITLE, title)
                putExtra(DETAIL_INFO, movie)
            }

            ActivityCompat.startActivity(activity, intent, null)
        }
    }
}
