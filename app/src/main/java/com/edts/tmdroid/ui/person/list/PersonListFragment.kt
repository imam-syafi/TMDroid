package com.edts.tmdroid.ui.person.list

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.edts.tmdroid.databinding.FragmentPersonListBinding
import com.edts.tmdroid.ui.common.BaseFragment
import com.edts.tmdroid.ui.ext.bind
import com.edts.tmdroid.ui.ext.showDialog
import com.edts.tmdroid.ui.ext.showErrorAlert
import com.zhuinden.liveevent.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PersonListFragment : BaseFragment<FragmentPersonListBinding>(
    FragmentPersonListBinding::inflate,
) {

    private val viewModel by viewModels<PersonListViewModel>()

    override fun FragmentPersonListBinding.setup() {
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.onRefresh()
            swipeRefreshLayout.isRefreshing = false
        }

        val personListAdapter = PersonListAdapter(
            onClick = { person ->
                val directions = PersonListFragmentDirections.toPersonDetailFragment(
                    title = person.name,
                    person = person,
                )

                findNavController().navigate(directions)
            },
            onLastItem = viewModel::fetchData,
        ).also(rvPeople::setAdapter)

        // UI = f(state)
        viewModel.state.observe(viewLifecycleOwner) { state ->
            loadingDialog.showDialog(state.isLoading)

            rvPeople.isVisible = state.fallback == null
            personListAdapter.submitList(state.people)

            vFallback.bind(state.fallback)
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) {
            showErrorAlert(it, viewModel::onRefresh)
        }
    }
}
