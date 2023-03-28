package com.edts.tmdroid.ui.home

import android.view.inputmethod.EditorInfo
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import com.edts.tmdroid.R
import com.edts.tmdroid.databinding.FragmentHomeBinding
import com.edts.tmdroid.ui.common.BaseFragment
import com.edts.tmdroid.ui.ext.on
import com.edts.tmdroid.ui.ext.onEndIconClick
import com.edts.tmdroid.ui.ext.setupOptionsMenu
import com.edts.tmdroid.ui.ext.showToast
import com.edts.tmdroid.ui.home.menu.GridItem
import com.edts.tmdroid.ui.home.menu.GridItem.Header
import com.edts.tmdroid.ui.home.menu.GridItem.IconMenu
import com.edts.tmdroid.ui.home.menu.IconMenuAdapter
import com.edts.tmdroid.ui.movie.list.MovieListType

class HomeFragment : BaseFragment<FragmentHomeBinding>(
    FragmentHomeBinding::inflate,
) {

    override fun FragmentHomeBinding.setup() {
        // Setup options menu
        setupOptionsMenu(R.menu.menu_main) { menuId ->
            when (menuId) {
                R.id.action_logout -> {
                    // TODO: Handle logout
                    true
                }
                else -> false
            }
        }

        // Handle search input
        tilSearch.onEndIconClick(::handleSearch)
        etSearch.on(EditorInfo.IME_ACTION_SEARCH, ::handleSearch)

        // Populate icon menu grid
        with(rvIconMenu) {
            val iconMenuItemList: List<GridItem> = listOf(
                Header(R.string.movies),
                IconMenu(
                    title = R.string.top_rated,
                    icon = R.drawable.icons8_imovie_50,
                    onClick = { toMovieList(MovieListType.TopRated) },
                ),
                IconMenu(
                    title = R.string.upcoming,
                    icon = R.drawable.icons8_movie_projector_50,
                    onClick = { toMovieList(MovieListType.Upcoming) },
                ),
                IconMenu(
                    title = R.string.now_playing,
                    icon = R.drawable.icons8_movie_theater_64,
                    onClick = { toMovieList(MovieListType.NowPlaying) },
                ),
                IconMenu(
                    title = R.string.popular,
                    icon = R.drawable.icons8_movie_ticket_50,
                    onClick = { toMovieList(MovieListType.Popular) },
                ),
                Header(title = R.string.tv_shows),
                IconMenu(
                    title = R.string.popular,
                    icon = R.drawable.icons8_retro_tv_filled_50,
                    onClick = ::todo,
                ),
                IconMenu(
                    title = R.string.top_rated,
                    icon = R.drawable.icons8_popcorn_64,
                    onClick = ::todo,
                ),
                IconMenu(
                    title = R.string.on_the_air,
                    icon = R.drawable.icons8_clapperboard_50,
                    onClick = ::todo,
                ),
                IconMenu(
                    title = R.string.airing_today,
                    icon = R.drawable.icons8_cinema_50,
                    onClick = ::todo,
                ),
                Header(title = R.string.people),
                IconMenu(
                    title = R.string.popular,
                    icon = R.drawable.icons8_charlie_chaplin_64,
                    onClick = {
                        val directions = HomeFragmentDirections.toPersonListFragment()
                        findNavController().navigate(directions)
                    },
                ),
            )

            adapter = IconMenuAdapter(iconMenuItemList)

            // Differentiate span size for heading text and icon menu items
            val gridLayoutManager = (layoutManager as GridLayoutManager)
            gridLayoutManager.spanSizeLookup = object : SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return when (iconMenuItemList[position]) {
                        is Header -> gridLayoutManager.spanCount
                        is IconMenu -> 1
                    }
                }
            }
        }
    }

    private fun handleSearch(query: String) {
        if (query.isNotBlank()) {
            toMovieList(MovieListType.Search(query))
        } else {
            showToast(R.string.empty_query)
        }
    }

    private fun toMovieList(movieListType: MovieListType) {
        val title = when (movieListType) {
            is MovieListType.Search -> getString(R.string.search_result, movieListType.query)
            else -> {
                val resId = when (movieListType) {
                    MovieListType.TopRated -> R.string.top_rated
                    MovieListType.Upcoming -> R.string.upcoming
                    MovieListType.NowPlaying -> R.string.now_playing
                    MovieListType.Popular -> R.string.popular
                    else -> throw IllegalArgumentException("Invalid type")
                }

                getString(R.string.movie_list_title, getString(resId))
            }
        }

        val directions = HomeFragmentDirections.toMovieListFragment(title, movieListType)
        findNavController().navigate(directions)
    }

    private fun todo() {
        showToast("Coming Soon")
    }
}
