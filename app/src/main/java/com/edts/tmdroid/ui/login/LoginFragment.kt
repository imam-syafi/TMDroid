package com.edts.tmdroid.ui.login

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.edts.tmdroid.R
import com.edts.tmdroid.databinding.FragmentLoginBinding
import com.edts.tmdroid.ui.common.BaseFragment
import com.edts.tmdroid.ui.ext.bind
import com.edts.tmdroid.ui.ext.hideKeyboard
import com.edts.tmdroid.ui.ext.resetFocus
import com.edts.tmdroid.ui.ext.showSnack
import com.edts.tmdroid.ui.ext.value
import com.edts.tmdroid.ui.model.LoginResult.NOT_REGISTERED
import com.edts.tmdroid.ui.model.LoginResult.OK
import com.edts.tmdroid.ui.model.LoginResult.PASSWORD_INCORRECT
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.zhuinden.liveevent.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(
    FragmentLoginBinding::inflate,
) {

    private val viewModel by viewModels<LoginViewModel>()

    override fun FragmentLoginBinding.setup() {
        cvName.bind(
            initialValue = viewModel.state.value?.name,
            onChange = viewModel::onNameChange,
            validate = String::isNotBlank,
            errorText = R.string.name_invalid,
        )

        cvPassword.bind(
            initialValue = viewModel.state.value?.password,
            onChange = viewModel::onPasswordChange,
            validate = String::isNotBlank,
            errorText = R.string.password_invalid,
        )

        btnLogin.setOnClickListener {
            hideKeyboard()
            viewModel.onSubmit(cvName.value, cvPassword.value)
        }

        // UI = f(state)
        viewModel.state.observe(viewLifecycleOwner) { state ->
            loading.isVisible = state.isLoading

            cvName.isEnabled = !state.isLoading
            cvPassword.isEnabled = !state.isLoading

            btnLogin.isEnabled = state.canSubmit
        }

        // Handle one-off events
        viewModel.event.observe(viewLifecycleOwner) { result ->
            cvPassword.error = null

            when (result) {
                OK -> {
                    showSnack(getString(R.string.welcome, cvName.value))
                }
                NOT_REGISTERED -> {
                    MaterialAlertDialogBuilder(requireContext())
                        .setTitle(R.string.account_not_found)
                        .setMessage(R.string.register_first)
                        .setPositiveButton(R.string.register) { _, _ ->
                            hideKeyboard()
                            viewModel.onRegister(cvName.value, cvPassword.value)
                        }
                        .show()
                }
                PASSWORD_INCORRECT -> {
                    cvPassword.error = getString(R.string.password_incorrect)
                }
            }
        }
    }

    private fun FragmentLoginBinding.hideKeyboard() {
        listOf(cvName, cvPassword).forEach { til ->
            til.editText?.let { et ->
                et.hideKeyboard()
                et.resetFocus()
            }
        }
    }
}
