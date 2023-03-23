package com.edts.tmdroid.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import com.edts.tmdroid.R
import com.edts.tmdroid.databinding.ActivityMainBinding
import com.edts.tmdroid.ext.on
import com.edts.tmdroid.ext.onEndIconClick
import com.edts.tmdroid.ui.detail.DetailActivity
import com.edts.tmdroid.ui.favorite.FavoriteActivity
import com.edts.tmdroid.ui.list.ListActivity
import com.edts.tmdroid.ui.main.menu.GridItem.Header
import com.edts.tmdroid.ui.main.menu.GridItem.IconMenu
import com.edts.tmdroid.ui.main.menu.IconMenuAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.setup()
    }

    private fun ActivityMainBinding.setup() {
        val items = listOf(
            Header(title = "Movies"),
            IconMenu(
                title = getString(R.string.top_rated),
                icon = R.drawable.icons8_imovie_50,
                action = {
                    ListActivity.open(this@MainActivity, getString(R.string.top_rated))
                },
            ),
            IconMenu(
                title = "Favorite",
                icon = R.drawable.icons8_favorite_48,
                action = {
                    FavoriteActivity.open(
                        this@MainActivity,
                        getString(R.string.favorite_movies),
                    )
                },
            ),
            IconMenu(
                title = "Upcoming",
                icon = R.drawable.icons8_movie_projector_50,
                action = {
                    DetailActivity.open(
                        this@MainActivity,
                        getString(R.string.movie_detail),
                    )
                },
            ),
            IconMenu(
                title = "Now Playing",
                icon = R.drawable.icons8_movie_theater_64,
                action = ::todo,
            ),
            IconMenu(
                title = "Popular",
                icon = R.drawable.icons8_movie_ticket_50,
                action = ::todo,
            ),
            Header(title = "TV Shows"),
            IconMenu(
                title = "Popular",
                icon = R.drawable.icons8_retro_tv_filled_50,
                action = ::todo,
            ),
            IconMenu(
                title = getString(R.string.top_rated),
                icon = R.drawable.icons8_popcorn_64,
                action = ::todo,
            ),
            IconMenu(
                title = "On the Air",
                icon = R.drawable.icons8_clapperboard_50,
                action = ::todo,
            ),
            IconMenu(
                title = "Airing Today",
                icon = R.drawable.icons8_cinema_50,
                action = ::todo,
            ),
            Header(title = "People"),
            IconMenu(
                title = "Popular",
                icon = R.drawable.icons8_charlie_chaplin_64,
                action = ::todo,
            ),
        )

        with(menuGrid) {
            adapter = IconMenuAdapter(items)

            val gridLayoutManager = (layoutManager as GridLayoutManager)
            gridLayoutManager.spanSizeLookup = object : SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return when (items[position]) {
                        is Header -> gridLayoutManager.spanCount
                        is IconMenu -> 1
                    }
                }
            }
        }

        searchLayout.onEndIconClick(::handleSearch)
        searchField.on(EditorInfo.IME_ACTION_SEARCH, ::handleSearch)
    }

    private fun handleSearch(query: String) {
        if (query.isNotBlank()) {
            ListActivity.open(
                this,
                getString(R.string.search_result, query),
                query
            )
        } else {
            Toast.makeText(this, getString(R.string.empty_query), Toast.LENGTH_SHORT).show()
        }
    }

    private fun todo() {
        Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show()
    }

    companion object {

        fun open(activity: AppCompatActivity) {
            val intent = Intent(activity, MainActivity::class.java)
            ActivityCompat.startActivity(activity, intent, null)
        }
    }
}
