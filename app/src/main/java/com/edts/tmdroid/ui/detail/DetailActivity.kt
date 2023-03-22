package com.edts.tmdroid.ui.detail

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import com.edts.tmdroid.R
import com.edts.tmdroid.data.Movie
import com.edts.tmdroid.databinding.ActivityDetailBinding
import com.edts.tmdroid.ext.loadFromUrl

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

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
        }
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
