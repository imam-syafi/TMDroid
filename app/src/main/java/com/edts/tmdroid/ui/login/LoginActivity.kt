package com.edts.tmdroid.ui.login

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.edit
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.doAfterTextChanged
import com.edts.tmdroid.R
import com.edts.tmdroid.databinding.ActivityLoginBinding
import com.edts.tmdroid.ext.getPrefs
import com.edts.tmdroid.ui.main.MainActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val title = intent.getStringExtra(PAGE_TITLE)
        supportActionBar?.title = title

        binding.setup()
    }

    private fun ActivityLoginBinding.setup() {
        etEmail.doAfterTextChanged { editable ->
            val email = editable.toString()
            val isValid = email.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
            tilEmail.error = if (isValid) null else getString(R.string.email_invalid)
            validate()
        }

        etPassword.doAfterTextChanged { editable ->
            val password = editable.toString()
            val isValid = password.isNotBlank()
            tilPassword.error = if (isValid) null else getString(R.string.password_invalid)
            validate()
        }

        btnLogin.setOnClickListener {
            val prefs = getPrefs()
            prefs.edit(commit = true) {
                putBoolean(getString(R.string.is_logged_in_key), true)
            }

            MainActivity.open(this@LoginActivity)
            finish()
        }
    }

    private fun ActivityLoginBinding.validate() {
        val validated = listOf(
            tilEmail.error,
            tilPassword.error,
        ).all(CharSequence?::isNullOrBlank)

        with(btnLogin) {
            isEnabled = validated
            backgroundTintList = ColorStateList.valueOf(
                ResourcesCompat.getColor(
                    resources,
                    if (validated) R.color.teal_700 else android.R.color.darker_gray,
                    theme,
                )
            )
        }
    }

    companion object {
        const val PAGE_TITLE = "page_title"

        fun open(activity: AppCompatActivity, title: String) {
            val intent = Intent(activity, LoginActivity::class.java).apply {
                putExtra(PAGE_TITLE, title)
            }

            ActivityCompat.startActivity(activity, intent, null)
        }
    }
}
