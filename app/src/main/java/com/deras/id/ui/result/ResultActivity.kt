package com.deras.id.ui.result

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        // Initialize views
        ivResultImage = findViewById(R.id.result_image)
        tvResult = findViewById(R.id.tvResult)
        tvCreatedAt = findViewById(R.id.tvCreatedAt)
        tvSuggestion = findViewById(R.id.tvSuggestion)
        tvExplanation = findViewById(R.id.tvExplanation)

        // Initialize ViewModel
        viewModel = ViewModelProvider(this)[DetectionViewModel::class.java]

        // Get data from intent
        val resultImageUri = intent.getStringExtra(EXTRA_IMAGE_URI)
        val result = intent.getStringExtra(EXTRA_RESULT)
        val createdAt = intent.getStringExtra(EXTRA_CREATED_AT)
        val suggestion = intent.getStringExtra(EXTRA_SUGGESTION)
        val explanation = intent.getStringExtra(EXTRA_EXPLANATION)
        val detectionId = intent.getStringExtra(EXTRA_DETECTION_ID)

        // Set data to views
        resultImageUri?.let {
            Glide.with(this)
                .load(it)
                .into(ivResultImage)
        }
        tvResult.text = result
        tvCreatedAt.text = createdAt
        tvSuggestion.text = suggestion
        tvExplanation.text = explanation

        // Call API to get detection result if detectionId is not null
        detectionId?.let {
            viewModel.getDetectionResult(it).observe(this) { response ->
                // Update UI with detection result data
                tvResult.text = response.data?.result ?: "Result not available"
                tvCreatedAt.text = response.data?.createdAt ?: "Created at not available"
                tvSuggestion.text = response.data?.suggestion ?: "Suggestion not available"
                tvExplanation.text = response.data?.explanation ?: "Explanation not available"
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
