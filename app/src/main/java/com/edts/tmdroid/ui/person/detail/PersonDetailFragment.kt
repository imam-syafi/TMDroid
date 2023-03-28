package com.edts.tmdroid.ui.person.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.fragment.navArgs
import com.edts.tmdroid.data.remote.NetworkModule
import com.edts.tmdroid.databinding.FragmentPersonDetailBinding
import com.edts.tmdroid.ui.ext.loadFromUrl
import com.edts.tmdroid.ui.ext.showToast

class PersonDetailFragment : Fragment() {

    private var _binding: FragmentPersonDetailBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<PersonDetailFragmentArgs>()
    private val viewModel by viewModels<PersonDetailViewModel> {
        viewModelFactory {
            initializer {
                val personId = args.person.id
                PersonDetailViewModel(personId, NetworkModule.tmdbService)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentPersonDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.setup()
    }

    private fun FragmentPersonDetailBinding.setup() {
        val person = args.person

        ivProfile.loadFromUrl(person.profileUrl)
        tvName.text = person.name

        rvKnownFor.adapter = KnownForListAdapter(
            onClick = { knownFor ->
                // TODO: Navigate to detail screen
                showToast(knownFor.title)
            },
        ).apply { submitList(person.knownFor) }

        // UI = f(state)
        viewModel.state.observe(viewLifecycleOwner) { state ->
            tvBio.text = state.personDetail?.biography
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
