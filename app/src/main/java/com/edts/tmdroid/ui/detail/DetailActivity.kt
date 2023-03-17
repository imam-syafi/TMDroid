package com.edts.tmdroid.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.edts.tmdroid.R
import com.edts.tmdroid.data.Movie
import com.edts.tmdroid.databinding.ActivityDetailBinding
import kotlin.properties.Delegates.observable

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private var index by observable(initialValue = -1) { _, _, curr ->
        binding.render(Movie.SAMPLES[curr])
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.poster.setOnClickListener {
            index = if (index < Movie.SAMPLES.lastIndex) {
                index + 1
            } else {
                0
            }
        }

        // to trigger delegate
        index = 0
    }

    private fun ActivityDetailBinding.render(movie: Movie) {
        backdrop.setImageResource(movie.backdrop)
        poster.setImageResource(movie.poster)
        title.text = movie.title
        releaseDate.text = movie.releaseDate
        rating.text = getString(R.string.rating, movie.voteAverage.toString(), movie.voteCount)
        overview.text = movie.overview
    }
}
