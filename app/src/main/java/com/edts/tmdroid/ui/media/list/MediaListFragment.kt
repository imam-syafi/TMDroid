package com.edts.tmdroid.ui.media.list

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.edts.tmdroid.R
import com.edts.tmdroid.data.common.MediaType
import com.edts.tmdroid.databinding.FragmentMediaListBinding
import com.edts.tmdroid.ui.common.BaseFragment
import com.edts.tmdroid.ui.ext.bind
import com.edts.tmdroid.ui.ext.showDialog
import com.edts.tmdroid.ui.ext.showErrorAlert
import com.edts.tmdroid.ui.model.Media
import com.zhuinden.liveevent.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MediaListFragment : BaseFragment<FragmentMediaListBinding>(
    FragmentMediaListBinding::inflate,
) {

    private val viewModel by viewModels<MediaListViewModel>()

    override fun FragmentMediaListBinding.setup() {
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.onRefresh()
            swipeRefreshLayout.isRefreshing = false
        }

        val mediaListAdapter = MediaListAdapter(
            onClick = {
                val (title, type) = when (it) {
                    is Media.Movie -> Pair(R.string.movie_detail, MediaType.Movie)
                    is Media.Tv -> Pair(R.string.tv_detail, MediaType.Tv)
                }

                val directions = MediaListFragmentDirections.toMediaDetailFragment(
                    title = getString(title),
                    mediaId = it.id,
                    mediaType = type,
                )

                findNavController().navigate(directions)
            },
        ).also(rvMedia::setAdapter)

        // UI = f(state)
        viewModel.state.observe(viewLifecycleOwner) { state ->
            svShimmer.isVisible = state.isLoading
            flShimmer.showShimmer(state.isLoading)
            loadingDialog.showDialog(state.isLoading)

            rvMedia.isVisible = !state.isLoading
            mediaListAdapter.submitList(state.media)

            vFallback.bind(state.fallback)
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) {
            showErrorAlert(it, viewModel::onRefresh)
        }
    }
}
