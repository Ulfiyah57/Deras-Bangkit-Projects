package com.deras.id.ui.result

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.deras.id.R
import com.deras.id.ui.viewModel.DetectionViewModel

class ResultActivity : AppCompatActivity() {

    private lateinit var ivResultImage: ImageView
    private lateinit var tvResult: TextView
    private lateinit var tvCreatedAt: TextView
    private lateinit var tvSuggestion: TextView
    private lateinit var tvExplanation: TextView

    private lateinit var viewModel: DetectionViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        // Initialize views
        ivResultImage = findViewById(R.id.result_image)
        tvResult = findViewById(R.id.tvResult)
        tvCreatedAt = findViewById(R.id.tvCreatedAt)
        tvSuggestion = findViewById(R.id.tvSuggestion)
        tvExplanation = findViewById(R.id.tvExplanation)

        // Initialize ViewModel using ViewModelProvider
        viewModel = ViewModelProvider(this).get(DetectionViewModel::class.java)

        // Get data from intent
        val resultImageUri = intent.getStringExtra(EXTRA_IMAGE_URI)
        val result = intent.getStringExtra(EXTRA_RESULT)
        val createdAt = intent.getStringExtra(EXTRA_CREATED_AT)
        val suggestion = intent.getStringExtra(EXTRA_SUGGESTION)
        val explanation = intent.getStringExtra(EXTRA_EXPLANATION)
        val detectionId = intent.getStringExtra(EXTRA_DETECTION_ID)

        // Set image using Glide if resultImageUri is not null
        resultImageUri?.let {
            Glide.with(this)
                .load(it)
                .into(ivResultImage)
        }

        // Set text values to TextViews
        tvResult.text = result
        tvCreatedAt.text = createdAt
        tvSuggestion.text = suggestion
        tvExplanation.text = explanation

        // Call API to get detection result if detectionId is not null
        detectionId?.let { id ->
            viewModel.getDetectionResult(id)
            viewModel.detectionResult.observe(this) { response ->
                if (response != null && response.isSuccessful) {
                    val data = response.body()?.data
                    // Update UI with detection result data
                    data?.let {
                        tvResult.text = it.result ?: "Result not available"
                        tvCreatedAt.text = it.createdAt ?: "Created at not available"
                        tvSuggestion.text = it.suggestion ?: "Suggestion not available"
                        tvExplanation.text = it.explanation ?: "Explanation not available"
                    }
                } else {
                    // Handle error scenario or null response
                    // Show error message or handle accordingly
                    tvResult.text = "Result not available"
                    tvCreatedAt.text = "Created at not available"
                    tvSuggestion.text = "Suggestion not available"
                    tvExplanation.text = "Explanation not available"
                }
            }
        }
    }

    companion object {
        const val EXTRA_IMAGE_URI = "extra_image_uri"
        const val EXTRA_RESULT = "extra_result"
        const val EXTRA_CREATED_AT = "extra_created_at"
        const val EXTRA_SUGGESTION = "extra_suggestion"
        const val EXTRA_EXPLANATION = "extra_explanation"
        const val EXTRA_DETECTION_ID = "extra_detection_id"
    }
}
