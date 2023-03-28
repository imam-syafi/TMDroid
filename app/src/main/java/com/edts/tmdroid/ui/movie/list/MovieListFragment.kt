package com.edts.tmdroid.ui.movie.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.edts.tmdroid.data.remote.NetworkModule
import com.edts.tmdroid.databinding.FragmentMovieListBinding
import com.edts.tmdroid.ui.model.Movie

class MovieListFragment : Fragment() {

    private var _binding: FragmentMovieListBinding? = null
    private val binding get() = _binding!!

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMovieListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.setup()
    }

    private fun FragmentMovieListBinding.setup() {
        val movieListAdapter = MovieListAdapter()
            .apply {
                delegate = object : MovieDelegate {

                    override fun onMovieClicked(movie: Movie) {
                        val directions = MovieListFragmentDirections.toMovieDetailFragment(movie)
                        findNavController().navigate(directions)
                    }
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
