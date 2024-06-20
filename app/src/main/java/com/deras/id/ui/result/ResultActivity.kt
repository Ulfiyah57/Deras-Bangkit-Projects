package com.deras.id.ui.result

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.deras.id.R
import com.deras.id.databinding.ActivityResultBinding
import com.deras.id.ui.viewModel.DetectionViewModel

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding
    private val viewModel: DetectionViewModel by viewModels()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_result)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val resultImageUri = intent.getStringExtra(EXTRA_IMAGE_URI)
        val result = intent.getStringExtra(EXTRA_RESULT)
        val createdAt = intent.getStringExtra(EXTRA_CREATED_AT)
        val suggestion = intent.getStringExtra(EXTRA_SUGGESTION)
        val explanation = intent.getStringExtra(EXTRA_EXPLANATION)
        val detectionId = intent.getStringExtra(EXTRA_DETECTION_ID)

        resultImageUri?.let {
            Glide.with(this)
                .load(it)
                .into(binding.resultImage)
        }

        binding.tvResult.text = result
        viewModel.createdAt.value = createdAt
        viewModel.suggestion.value = suggestion
        viewModel.explanation.value = explanation

        detectionId?.let { id ->
            viewModel.getDetectionResult(id)
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