package com.edts.tmdroid.ui.watchlist

import androidx.fragment.app.viewModels
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.fragment.findNavController
import com.edts.tmdroid.data.common.MediaType
import com.edts.tmdroid.data.local.AppDatabase
import com.edts.tmdroid.databinding.FragmentWatchListBinding
import com.edts.tmdroid.ui.common.BaseFragment

class WatchListFragment : BaseFragment<FragmentWatchListBinding>(
    FragmentWatchListBinding::inflate,
) {

    private val viewModel by viewModels<WatchListViewModel> {
        viewModelFactory {
            initializer {
                WatchListViewModel(
                    queueDao = AppDatabase
                        .getInstance(requireContext())
                        .queueDao(),
                )
            }
        }
    }

    override fun FragmentWatchListBinding.setup() {
        val watchListAdapter = WatchListAdapter(
            onClick = { queue ->
                when (queue.mediaType) {
                    MediaType.Movie -> {
                        val directions = WatchListFragmentDirections.toMovieDetailFragment(
                            movieId = queue.mediaId,
                        )

                        findNavController().navigate(directions)
                    }
                    MediaType.Tv -> {
                        // TODO: Handle TV shows
                    }
                }
            },
            onDelete = viewModel::onDelete,
        ).also(rvWatchList::setAdapter)

        viewModel.watchList.observe(viewLifecycleOwner, watchListAdapter::submitList)
    }
}
