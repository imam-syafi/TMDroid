package com.edts.tmdroid.ui.person.detail

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.edts.tmdroid.R
import com.edts.tmdroid.data.common.MediaType
import com.edts.tmdroid.databinding.FragmentPersonDetailBinding
import com.edts.tmdroid.ui.common.BaseFragment
import com.edts.tmdroid.ui.ext.loadFromUrl
import com.edts.tmdroid.ui.ext.setToggleMaxLines
import com.edts.tmdroid.ui.ext.showDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PersonDetailFragment : BaseFragment<FragmentPersonDetailBinding>(
    FragmentPersonDetailBinding::inflate,
) {

    private val args by navArgs<PersonDetailFragmentArgs>()
    private val viewModel by viewModels<PersonDetailViewModel>()

    override fun FragmentPersonDetailBinding.setup() {
        val person = args.person

        ivProfile.loadFromUrl(person.profileUrl)
        tvName.text = person.name
        tvBio.setToggleMaxLines(initial = tvBio.maxLines)

        rvKnownFor.adapter = KnownForListAdapter(
            onClick = { knownFor ->
                val directions = PersonDetailFragmentDirections.toMediaDetailFragment(
                    title = getString(
                        when (knownFor.mediaType) {
                            MediaType.Movie -> R.string.movie_detail
                            MediaType.Tv -> R.string.tv_detail
                        },
                    ),
                    mediaId = knownFor.id,
                    mediaType = knownFor.mediaType,
                )

                findNavController().navigate(directions)
            },
        ).apply { submitList(person.knownFor) }

        // UI = f(state)
        viewModel.state.observe(viewLifecycleOwner) { state ->
            loadingDialog.showDialog(state.isLoading)

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
