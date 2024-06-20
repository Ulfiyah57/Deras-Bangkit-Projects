package com.deras.id.ui.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.deras.id.R
import com.deras.id.databinding.ActivityMainBinding
import com.deras.id.ui.history.HistoryActivity
import com.deras.id.ui.camera.CameraActivity
import com.deras.id.utils.Constanta
import com.deras.id.utils.Helper

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Hide abnormal layer in bottom navigation
        binding.bottomNavigationView.background = null

        // Set bottom navigation item selection listener
        binding.bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    // Do nothing, already on home
                    true
                }
                R.id.navigation_detection -> {
                    checkCameraPermission()
                }
                R.id.navigation_history -> {
                    checkStoragePermission()
                }else -> false
            }
        }
    }

    private fun checkCameraPermission(): Boolean {
        return if (Helper.isPermissionGranted(this, Manifest.permission.CAMERA)) {
            startActivity(Intent(this, CameraActivity::class.java))
            true
        } else {
            ActivityCompat.requestPermissions(
                this@MainActivity,
                arrayOf(Manifest.permission.CAMERA),
                Constanta.CAMERA_PERMISSION_CODE
            )
            false
        }
    }

    private fun checkStoragePermission(): Boolean {
        return if (Helper.isPermissionGranted(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            startActivity(Intent(this, HistoryActivity::class.java))
            true
        } else {
            ActivityCompat.requestPermissions(
                this@MainActivity,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                Constanta.STORAGE_PERMISSION_CODE
            )
            false
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            Constanta.CAMERA_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    Helper.notifyGivePermission(this, "Berikan aplikasi izin mengakses kamera")
                }
            }
            Constanta.STORAGE_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    Helper.notifyGivePermission(this, "Berikan aplikasi izin storage untuk membaca dan menyimpan story")
                }
            }
        }
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
}