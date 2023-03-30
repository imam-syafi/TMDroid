package com.edts.tmdroid.ui.review

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.edts.tmdroid.R
import com.edts.tmdroid.databinding.FragmentReviewBinding
import com.edts.tmdroid.ui.common.BaseFragment
import com.edts.tmdroid.ui.ext.bind

class ReviewEditorFragment : BaseFragment<FragmentReviewBinding>(
    FragmentReviewBinding::inflate,
) {

    private val viewModel by viewModels<ReviewEditorViewModel>()

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
            // TODO: Insert or update review

            findNavController().navigateUp()
        }

        // UI = f(state)
        viewModel.state.observe(viewLifecycleOwner) { state ->
            btnSubmit.isEnabled = state.canSubmit
        }
    }
}
