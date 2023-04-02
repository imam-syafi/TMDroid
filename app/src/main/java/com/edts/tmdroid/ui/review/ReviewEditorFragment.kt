package com.edts.tmdroid.ui.review

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.edts.tmdroid.R
import com.edts.tmdroid.databinding.FragmentReviewBinding
import com.edts.tmdroid.ui.common.BaseFragment
import com.edts.tmdroid.ui.ext.bind
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewEditorFragment : BaseFragment<FragmentReviewBinding>(
    FragmentReviewBinding::inflate,
) {

    private val args by navArgs<ReviewEditorFragmentArgs>()
    private val viewModel by viewModels<ReviewEditorViewModel>()

    override fun FragmentReviewBinding.setup() {
        tilName.bind(
            initialValue = viewModel.state.value?.name ?: args.review?.name,
            errorText = R.string.name_invalid,
            validate = String::isNotBlank,
            onChange = viewModel::onNameChange,
        )

        tilComment.bind(
            initialValue = viewModel.state.value?.comment ?: args.review?.comment,
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
