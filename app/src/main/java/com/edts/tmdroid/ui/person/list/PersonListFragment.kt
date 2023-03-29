package com.edts.tmdroid.ui.person.list

import androidx.fragment.app.viewModels
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.fragment.findNavController
import com.edts.tmdroid.data.remote.NetworkModule
import com.edts.tmdroid.databinding.FragmentPersonListBinding
import com.edts.tmdroid.ui.common.BaseFragment
import com.edts.tmdroid.ui.ext.showDialog

class PersonListFragment : BaseFragment<FragmentPersonListBinding>(
    FragmentPersonListBinding::inflate,
) {

    private val viewModel by viewModels<PersonListViewModel> {
        viewModelFactory {
            initializer {
                PersonListViewModel(NetworkModule.tmdbService)
            }
        }
    }

    override fun FragmentPersonListBinding.setup() {
        val personListAdapter = PersonListAdapter(
            onClick = { person ->
                val directions = PersonListFragmentDirections.toPersonDetailFragment(
                    title = person.name,
                    person = person,
                )

                findNavController().navigate(directions)
            },
        ).also(rvPeople::setAdapter)

        // UI = f(state)
        viewModel.state.observe(viewLifecycleOwner) { state ->
            loadingDialog.showDialog(state.isLoading)
            personListAdapter.submitList(state.people)
        }
    }
}
