package com.edts.tmdroid.ui.person.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.edts.tmdroid.databinding.FragmentPersonDetailBinding
import com.edts.tmdroid.ext.loadFromUrl
import com.edts.tmdroid.ext.showToast

class PersonDetailFragment : Fragment() {

    private var _binding: FragmentPersonDetailBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<PersonDetailFragmentArgs>()

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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
