package com.edts.tmdroid.ui.person.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.edts.tmdroid.data.remote.NetworkModule
import com.edts.tmdroid.databinding.FragmentPersonListBinding
import com.edts.tmdroid.ext.showToast

class PersonListFragment : Fragment() {

    private var _binding: FragmentPersonListBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<PersonListViewModel> {
        viewModelFactory {
            initializer {
                PersonListViewModel(NetworkModule.tmdbService)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentPersonListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.setup()
    }

    private fun FragmentPersonListBinding.setup() {
        val personListAdapter = PersonListAdapter(
            onClick = { person ->
                // TODO: Navigate to detail screen
                showToast(person.name)
            },
        ).also(rvPeople::setAdapter)

        // UI = f(state)
        viewModel.state.observe(viewLifecycleOwner) { state ->
            personListAdapter.submitList(state.people)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
