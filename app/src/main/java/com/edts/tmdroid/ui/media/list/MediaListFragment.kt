package com.edts.tmdroid.ui.media.list

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.edts.tmdroid.databinding.FragmentMediaListBinding
import com.edts.tmdroid.ui.common.BaseFragment
import com.edts.tmdroid.ui.ext.showDialog
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
                // TODO: Navigate to detail screen
            },
        ).also(rvMedia::setAdapter)

        // UI = f(state)
        viewModel.state.observe(viewLifecycleOwner) { state ->
            svShimmer.isVisible = state.isLoading
            flShimmer.showShimmer(state.isLoading)
            loadingDialog.showDialog(state.isLoading)

            rvMedia.isVisible = !state.isLoading
            mediaListAdapter.submitList(state.media)
        }
    }
}
