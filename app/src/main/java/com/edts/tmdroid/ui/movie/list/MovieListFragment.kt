package com.edts.tmdroid.ui.movie.list

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.edts.tmdroid.databinding.FragmentMediaListBinding
import com.edts.tmdroid.ui.common.BaseFragment
import com.edts.tmdroid.ui.ext.showDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieListFragment : BaseFragment<FragmentMediaListBinding>(
    FragmentMediaListBinding::inflate,
) {

    private val viewModel by viewModels<MovieListViewModel>()

    override fun FragmentMediaListBinding.setup() {
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.onRefresh()

            // Hide it immediately, we've already got both shimmer & loading dialog to indicate
            // busy state
            swipeRefreshLayout.isRefreshing = false
        }

        val movieListAdapter = MovieListAdapter(
            onClick = { movie ->
                val directions = MovieListFragmentDirections.toMovieDetailFragment(movie.id)
                findNavController().navigate(directions)
            },
        ).also(rvMedia::setAdapter)

        // UI = f(state)
        viewModel.state.observe(viewLifecycleOwner) { state ->
            svShimmer.isVisible = state.isLoading
            flShimmer.showShimmer(state.isLoading)
            loadingDialog.showDialog(state.isLoading)

            rvMedia.isVisible = !state.isLoading
            movieListAdapter.submitList(state.movies)
        }
    }
}
