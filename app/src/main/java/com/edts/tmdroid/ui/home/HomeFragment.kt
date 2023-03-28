package com.edts.tmdroid.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import com.edts.tmdroid.R
import com.edts.tmdroid.databinding.FragmentHomeBinding
import com.edts.tmdroid.ext.on
import com.edts.tmdroid.ext.onEndIconClick
import com.edts.tmdroid.ext.setupOptionsMenu
import com.edts.tmdroid.ext.showToast
import com.edts.tmdroid.ui.home.menu.GridItem
import com.edts.tmdroid.ui.home.menu.GridItem.Header
import com.edts.tmdroid.ui.home.menu.GridItem.IconMenu
import com.edts.tmdroid.ui.home.menu.IconMenuAdapter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.setup()
    }

    private fun FragmentHomeBinding.setup() {
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
                    onClick = ::toMovieList,
                ),
                IconMenu(
                    title = R.string.upcoming,
                    icon = R.drawable.icons8_movie_projector_50,
                    onClick = ::todo,
                ),
                IconMenu(
                    title = R.string.now_playing,
                    icon = R.drawable.icons8_movie_theater_64,
                    onClick = ::todo,
                ),
                IconMenu(
                    title = R.string.popular,
                    icon = R.drawable.icons8_movie_ticket_50,
                    onClick = ::todo,
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
                    onClick = ::todo,
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
            // TODO: Navigate to movie list screen
            showToast(query)
        } else {
            showToast(R.string.empty_query)
        }
    }

    private fun toMovieList() {
        findNavController().navigate(R.id.to_movieListFragment)
    }

    private fun todo() {
        showToast("Coming Soon")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
