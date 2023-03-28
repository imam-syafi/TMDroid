package com.edts.tmdroid.ui.person.detail

import androidx.fragment.app.viewModels
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.fragment.navArgs
import com.edts.tmdroid.data.remote.NetworkModule
import com.edts.tmdroid.databinding.FragmentPersonDetailBinding
import com.edts.tmdroid.ui.common.BaseFragment
import com.edts.tmdroid.ui.ext.loadFromUrl
import com.edts.tmdroid.ui.ext.setToggleMaxLines
import com.edts.tmdroid.ui.ext.showToast

class PersonDetailFragment : BaseFragment<FragmentPersonDetailBinding>(
    FragmentPersonDetailBinding::inflate,
) {

    private val args by navArgs<PersonDetailFragmentArgs>()
    private val viewModel by viewModels<PersonDetailViewModel> {
        viewModelFactory {
            initializer {
                val personId = args.person.id
                PersonDetailViewModel(personId, NetworkModule.tmdbService)
            }
        }
    }

    override fun FragmentPersonDetailBinding.setup() {
        val person = args.person

        ivProfile.loadFromUrl(person.profileUrl)
        tvName.text = person.name
        tvBio.setToggleMaxLines(initial = tvBio.maxLines)

        rvKnownFor.adapter = KnownForListAdapter(
            onClick = { knownFor ->
                // TODO: Navigate to detail screen
                showToast(knownFor.title)
            },
        ).apply { submitList(person.knownFor) }

        // UI = f(state)
        viewModel.state.observe(viewLifecycleOwner) { state ->
            state.personDetail?.let {
                tvGenderAndDepartment.text = it.genderAndDepartment
                tvAge.text = it.birthDayAndAge
                tvBirthPlace.text = it.birthPlace
                tvAka.text = it.alsoKnownAs
                tvBio.text = it.biography
            }
        }
    }
}
