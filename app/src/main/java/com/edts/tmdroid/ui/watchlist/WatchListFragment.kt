package com.edts.tmdroid.ui.watchlist

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.edts.tmdroid.R
import com.edts.tmdroid.data.common.MediaType
import com.edts.tmdroid.databinding.FragmentWatchListBinding
import com.edts.tmdroid.ui.common.BaseFragment
import com.edts.tmdroid.ui.ext.addDivider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WatchListFragment : BaseFragment<FragmentWatchListBinding>(
    FragmentWatchListBinding::inflate,
) {

    private val viewModel by viewModels<WatchListViewModel>()

    override fun FragmentWatchListBinding.setup() {
        val watchListAdapter = WatchListAdapter(
            onClick = { queue ->
                val directions = WatchListFragmentDirections.toMediaDetailFragment(
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
            onDelete = viewModel::onDelete,
        )

        with(rvWatchList) {
            addDivider(R.drawable.divider)
            adapter = watchListAdapter
        }

        viewModel.watchList.observe(viewLifecycleOwner, watchListAdapter::submitList)
    }
}
