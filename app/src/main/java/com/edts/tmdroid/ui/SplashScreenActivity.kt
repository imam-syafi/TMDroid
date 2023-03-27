package com.edts.tmdroid.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.edts.tmdroid.R
import com.edts.tmdroid.databinding.ActivitySplashScreenBinding
import com.edts.tmdroid.ext.getPrefs
import com.edts.tmdroid.ui.home.HomeActivity
import com.edts.tmdroid.ui.login.LoginActivity

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.setup()
    }

    private fun ActivitySplashScreenBinding.setup() {
        logo
            .animate()
            .setDuration(1500)
            .alpha(1f)
            .withEndAction {
                authenticate()
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                finish()
            }
    }

    private fun authenticate() {
        val prefs = getPrefs()
        val isLoggedIn = prefs.getBoolean(
            getString(R.string.is_logged_in_key),
            false,
        )

        if (isLoggedIn) {
            HomeActivity.open(this)
        } else {
            LoginActivity.open(this, getString(R.string.login))
        }
    }
}
