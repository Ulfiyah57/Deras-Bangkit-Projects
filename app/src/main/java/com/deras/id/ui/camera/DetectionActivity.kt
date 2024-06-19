package com.deras.id.ui.camera

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.deras.id.R
import com.deras.id.ui.result.ResultActivity
import com.deras.id.ui.viewModel.DetectionViewModel
import java.io.File

@Suppress("DEPRECATION")
class DetectionActivity : AppCompatActivity() {

    private lateinit var detectionImageView: ImageView
    private lateinit var uploadButton: Button
    private val detectionViewModel: DetectionViewModel by viewModels()
    private var selectedImageFile: File? = null

    companion object {
        const val EXTRA_PHOTO_RESULT = "extra_photo_result"
        const val EXTRA_CAMERA_MODE = "extra_camera_mode"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detection)

        // Initialize views
        detectionImageView = findViewById(R.id.detection_image)
        uploadButton = findViewById(R.id.btn_upload)

        // Set initial image or perform initial setup for ImageView
        selectedImageFile = intent.getSerializableExtra(EXTRA_PHOTO_RESULT) as File?
        selectedImageFile?.let {
            detectionImageView.setImageURI(Uri.fromFile(it))
        } ?: run {
            detectionImageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.images_empty))
        }

        // Set up button click listener
        uploadButton.setOnClickListener {
            selectedImageFile?.let { file ->
                uploadImage(file)
            } ?: run {
                Toast.makeText(this, "Please select an image first", Toast.LENGTH_SHORT).show()
            }
        }

        // Observe the upload result
        detectionViewModel.uploadResult.observe(this) { responseDetection ->
            responseDetection?.data?.let { data ->
                val intent = Intent(this, ResultActivity::class.java).apply {
                    putExtra("result", data.result)
                    putExtra("createdAt", data.createdAt)
                    putExtra("suggestion", data.suggestion)
                    putExtra("explanation", data.explanation)
                }
                startActivity(intent)
            } ?: run {
                Toast.makeText(
                    this,
                    responseDetection?.message ?: "Upload failed",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun uploadImage(file: File) {
        val uri = Uri.fromFile(file)
        detectionViewModel.uploadImage(uri, "Image description")
    }
}
