package com.edts.tmdroid.ui.review

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.edts.tmdroid.R
import com.edts.tmdroid.data.local.AppDatabase
import com.edts.tmdroid.databinding.FragmentReviewBinding
import com.edts.tmdroid.ui.common.BaseFragment
import com.edts.tmdroid.ui.ext.bind

class ReviewEditorFragment : BaseFragment<FragmentReviewBinding>(
    FragmentReviewBinding::inflate,
) {

    private val args by navArgs<ReviewEditorFragmentArgs>()
    private val viewModel by viewModels<ReviewEditorViewModel> {
        viewModelFactory {
            initializer {
                ReviewEditorViewModel(
                    movieId = args.movieId,
                    review = args.review,
                    reviewDao = AppDatabase
                        .getInstance(requireContext())
                        .reviewDao(),
                )
            }
        }
    }

    override fun FragmentReviewBinding.setup() {
        // Sync up text input with view model, akin to controlled components in React
        tilName.bind(
            initialValue = viewModel.state.value?.name,
            errorText = R.string.name_invalid,
            validate = String::isNotBlank,
            onChange = viewModel::onNameChange,
        )

        tilComment.bind(
            initialValue = viewModel.state.value?.comment,
            errorText = R.string.comment_invalid,
            validate = String::isNotBlank,
            onChange = viewModel::onCommentChange,
        )

        btnSubmit.setOnClickListener {
            viewModel.onSubmit(
                name = etName.text.toString(),
                comment = etComment.text.toString(),
            )

            findNavController().navigateUp()
        }

        if (args.review != null) {
            btnSubmit.setText(R.string.update)

            btnDelete.isVisible = true
            btnDelete.setOnClickListener {
                viewModel.onDelete()
                findNavController().navigateUp()
            }
        }

        // UI = f(state)
        viewModel.state.observe(viewLifecycleOwner) { state ->
            btnSubmit.isEnabled = state.canSubmit
        }
    }
}
