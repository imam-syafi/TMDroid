package com.edts.tmdroid.ui.list

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.edts.tmdroid.R
import com.edts.tmdroid.data.Movie
import com.edts.tmdroid.databinding.ActivityListBinding
import com.edts.tmdroid.ui.detail.DetailActivity

class ListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListBinding

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
    }

    private fun ActivityListBinding.setup() {
        rvMovies.adapter = MovieAdapter().apply {
            setData(Movie.SAMPLES)

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

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    companion object {
        const val PAGE_TITLE = "page_title"

        fun open(activity: AppCompatActivity, title: String) {
            val intent = Intent(activity, ListActivity::class.java).apply {
                putExtra(PAGE_TITLE, title)
            }

            ActivityCompat.startActivity(activity, intent, null)
        }
    }
}
