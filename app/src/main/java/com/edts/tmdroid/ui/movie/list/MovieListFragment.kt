package com.edts.tmdroid.ui.movie.list

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.edts.tmdroid.data.remote.NetworkModule
import com.edts.tmdroid.databinding.FragmentMovieListBinding
import com.edts.tmdroid.ui.common.BaseFragment
import com.edts.tmdroid.ui.model.Movie

class MovieListFragment : BaseFragment<FragmentMovieListBinding>(
    FragmentMovieListBinding::inflate,
) {

    private val args by navArgs<MovieListFragmentArgs>()
    private val viewModel by viewModels<MovieListViewModel> {
        viewModelFactory {
            initializer {
                MovieListViewModel(
                    args.movieListType,
                    NetworkModule.tmdbService,
                )
            }
        }
    }

    override fun FragmentMovieListBinding.setup() {
        val movieListAdapter = MovieListAdapter()
            .apply {
                delegate = MovieDelegate { movie ->
                    val directions = MovieListFragmentDirections.toMovieDetailFragment(movie)
                    findNavController().navigate(directions)
                }
            }
            .also(rvMovie::setAdapter)

        // UI = f(state)
        viewModel.state.observe(viewLifecycleOwner) { state ->
            svShimmer.isVisible = state.isLoading
            flShimmer.showShimmer(state.isLoading)

            rvMovie.isVisible = !state.isLoading
            movieListAdapter.submitList(state.movies)
        }
    }
}
