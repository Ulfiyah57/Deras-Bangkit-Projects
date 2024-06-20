package com.deras.id.ui.main

import android.Manifest
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.deras.id.R
import com.deras.id.databinding.ActivityMainBinding
import com.deras.id.ui.article.ArticelFragment
import com.deras.id.ui.home.HomeFragment
import com.deras.id.ui.login.LoginActivity
import com.deras.id.ui.profile.ProfileFragment

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences

    companion object {
        private const val PREF_NAME = "login_pref"
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

        // Hide abnormal layer in bottom navigation
        binding.bottomNavigationView.background = null

        // Set initial fragment
        switchFragment(HomeFragment())

        // Set bottom navigation item selection listener
        binding.bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    switchFragment(HomeFragment())
                    true
                }
                R.id.navigation_article -> {
                    switchFragment(ArticelFragment())
                    true
                }
                R.id.navigation_profile -> {
                    switchFragment(ProfileFragment())
                    true
                }
                else -> false
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // Check if the user is already logged in
        val isLoggedIn = sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
        if (!isLoggedIn) {
            navigateToLogin()
        }
    }

    private fun switchFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }


    @Deprecated(
        "This method has been deprecated in favor of using the\n" +
                "      {@link OnBackPressedDispatcher} via {@link #getOnBackPressedDispatcher()}.\n" +
                "      The OnBackPressedDispatcher controls how back button events are dispatched\n" +
                "      to one or more {@link OnBackPressedCallback} objects."
    )
    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }
}
