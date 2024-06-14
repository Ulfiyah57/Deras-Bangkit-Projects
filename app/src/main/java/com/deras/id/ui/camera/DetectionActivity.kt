package com.deras.id.ui.camera


import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.deras.id.databinding.ActivityDetectionBinding
import com.deras.id.utils.Helper
import java.io.File

@Suppress("DEPRECATION")
class DetectionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetectionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /* get file from previous action -> camera / gallery */
        val myFile = intent?.getSerializableExtra(EXTRA_PHOTO_RESULT) as File
        val isBackCamera = this.intent?.getBooleanExtra(EXTRA_CAMERA_MODE, true) as Boolean
        val rotatedBitmap = Helper.rotateBitmap(
            BitmapFactory.decodeFile(myFile.path),
            isBackCamera
        )
        binding.detectionImage.setImageBitmap(rotatedBitmap)
        binding.btnUpload.setOnClickListener {}
    }

    companion object {
        const val EXTRA_PHOTO_RESULT = "PHOTO_RESULT"
        const val EXTRA_CAMERA_MODE = "CAMERA_MODE"
    }
}