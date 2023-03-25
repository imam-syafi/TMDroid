package com.edts.tmdroid.ui.login

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.edit
import androidx.core.content.res.ResourcesCompat
import com.edts.tmdroid.R
import com.edts.tmdroid.databinding.ActivityLoginBinding
import com.edts.tmdroid.ext.getPrefs
import com.edts.tmdroid.ui.home.HomeActivity

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
        cvEmail.onChange = { email, isValid ->
            // TODO: Do something with [email]

            // Ignore [isValid], for now, evaluate validation result manually
            evaluate()
        }

        cvPassword.onChange = { password, isValid ->
            // TODO: Do something with [password]

            // Ignore [isValid], for now, evaluate validation result manually
            evaluate()
        }

        btnLogin.setOnClickListener {
            val prefs = getPrefs()
            prefs.edit(commit = true) {
                putBoolean(getString(R.string.is_logged_in_key), true)
            }

            HomeActivity.open(this@LoginActivity)
            finish()
        }
    }

    private fun ActivityLoginBinding.evaluate() {
        val validated = listOf(
            cvEmail.isValid,
            cvPassword.isValid,
        ).all { it }

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
