package com.edts.tmdroid.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.edit
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
        btnLogin.setOnClickListener {
            val prefs = getPrefs()
            prefs.edit(commit = true) {
                putBoolean(getString(R.string.is_logged_in_key), true)
            }

            MainActivity.open(this@LoginActivity)
            finish()
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