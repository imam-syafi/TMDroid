package com.edts.tmdroid.ui.home

import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import com.edts.tmdroid.R
import com.edts.tmdroid.data.common.MediaType
import com.edts.tmdroid.databinding.FragmentHomeBinding
import com.edts.tmdroid.ui.common.BaseFragment
import com.edts.tmdroid.ui.ext.bind
import com.edts.tmdroid.ui.ext.on
import com.edts.tmdroid.ui.ext.onEndIconClick
import com.edts.tmdroid.ui.ext.setupOptionsMenu
import com.edts.tmdroid.ui.ext.showToast
import com.edts.tmdroid.ui.home.menu.GridItem
import com.edts.tmdroid.ui.home.menu.GridItem.Header
import com.edts.tmdroid.ui.home.menu.GridItem.IconMenu
import com.edts.tmdroid.ui.home.menu.IconMenuAdapter
import com.edts.tmdroid.ui.model.MediaListType
import com.edts.tmdroid.ui.model.PersonListType
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(
    FragmentHomeBinding::inflate,
) {

    private val viewModel by viewModels<HomeViewModel>()

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
                    onClick = {
                        val category = getString(R.string.top_rated)
                        toMediaList(
                            title = getString(R.string.movie_list_title, category),
                            mediaListType = MediaListType.MovieTopRated,
                        )
                    },
                ),
                IconMenu(
                    title = R.string.upcoming,
                    icon = R.drawable.icons8_movie_projector_50,
                    onClick = {
                        val category = getString(R.string.upcoming)
                        toMediaList(
                            title = getString(R.string.movie_list_title, category),
                            mediaListType = MediaListType.MovieUpcoming,
                        )
                    },
                ),
                IconMenu(
                    title = R.string.now_playing,
                    icon = R.drawable.icons8_movie_theater_64,
                    onClick = {
                        val category = getString(R.string.now_playing)
                        toMediaList(
                            title = getString(R.string.movie_list_title, category),
                            mediaListType = MediaListType.MovieNowPlaying,
                        )
                    },
                ),
                IconMenu(
                    title = R.string.popular,
                    icon = R.drawable.icons8_movie_ticket_50,
                    onClick = {
                        val category = getString(R.string.popular)
                        toMediaList(
                            title = getString(R.string.movie_list_title, category),
                            mediaListType = MediaListType.MoviePopular,
                        )
                    },
                ),
                Header(title = R.string.tv_shows),
                IconMenu(
                    title = R.string.popular,
                    icon = R.drawable.icons8_retro_tv_filled_50,
                    onClick = {
                        val category = getString(R.string.popular)
                        toMediaList(
                            title = getString(R.string.tv_list_title, category),
                            mediaListType = MediaListType.TvPopular,
                        )
                    },
                ),
                IconMenu(
                    title = R.string.top_rated,
                    icon = R.drawable.icons8_popcorn_64,
                    onClick = {
                        val category = getString(R.string.top_rated)
                        toMediaList(
                            title = getString(R.string.tv_list_title, category),
                            mediaListType = MediaListType.TvTopRated,
                        )
                    },
                ),
                IconMenu(
                    title = R.string.on_the_air,
                    icon = R.drawable.icons8_clapperboard_50,
                    onClick = {
                        val category = getString(R.string.on_the_air)
                        toMediaList(
                            title = getString(R.string.tv_list_title, category),
                            mediaListType = MediaListType.TvOnTheAir,
                        )
                    },
                ),
                IconMenu(
                    title = R.string.airing_today,
                    icon = R.drawable.icons8_cinema_50,
                    onClick = {
                        val category = getString(R.string.airing_today)
                        toMediaList(
                            title = getString(R.string.tv_list_title, category),
                            mediaListType = MediaListType.TvAiringToday,
                        )
                    },
                ),
                Header(title = R.string.people),
                IconMenu(
                    title = R.string.popular,
                    icon = R.drawable.icons8_charlie_chaplin_64,
                    onClick = {
                        val directions = HomeFragmentDirections.toPersonListFragment(
                            title = getString(R.string.popular_people),
                            personListType = PersonListType.Popular,
                        )

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

        // Setup watchlist
        val watchListAdapter = WatchListAdapter(
            onClick = { queue ->
                val directions = HomeFragmentDirections.toMediaDetailFragment(
                    title = getString(
                        when (queue.mediaType) {
                            MediaType.Movie -> R.string.movie_detail
                            MediaType.Tv -> R.string.tv_detail
                        },
                    ),
                    mediaId = queue.mediaId,
                    mediaType = queue.mediaType,
                )

                findNavController().navigate(directions)
            },
        ).also(rvWatchList::setAdapter)

        tvShowAll.setOnClickListener {
            val directions = HomeFragmentDirections.toWatchListFragment()
            findNavController().navigate(directions)
        }

        // UI = f(state)
        viewModel.state.observe(viewLifecycleOwner) { state ->
            watchListAdapter.submitList(state.queueList)
            vFallback.bind(state.fallback)
        }
    }

    private fun handleSearch(query: String) {
        if (query.isNotBlank()) {
            val directions = HomeFragmentDirections.toSearchFragment(
                title = getString(R.string.search_result, query),
                query = query,
            )

            findNavController().navigate(directions)
        } else {
            showToast(R.string.empty_query)
        }
    }

    private fun toMediaList(title: String, mediaListType: MediaListType) {
        val directions = HomeFragmentDirections.toMediaListFragment(
            title,
            mediaListType,
        )

        findNavController().navigate(directions)
    }
}
