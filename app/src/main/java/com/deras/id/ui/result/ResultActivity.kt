package com.deras.id.ui.result

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.deras.id.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding

    private val viewModel: ResultViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageUri = Uri.parse(intent.getStringExtra(EXTRA_IMAGE_URI))
        val result = intent.getStringExtra(EXTRA_RESULT)

        imageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.resultImage.setImageURI(it)
        }

        result?.let {
            Log.d("Result", "showResult: $it")
            binding.resultText.text = it
        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvNews.layoutManager = layoutManager

        viewModel.article.observe(this){
            when(it){
                is ResultState.Loading -> {
                    binding.shimmerLayout.startShimmer()
                }
                is ResultState.Success -> {
                    showRecyclerView()
                    setNewsData(it.data)
                }
                is ResultState.Error -> {
                    Log.e("ResultActivity", "Error: $it")
                    Toast.makeText(this, "Error: $it", Toast.LENGTH_SHORT).show()
                }
            }
        }


    }

    private fun setNewsData(consumer: List<ArticlesItem?>?){
        val adapter = ArticleAdapter()
        adapter.submitList(consumer)
        binding.rvNews.adapter = adapter
    }

    private fun showRecyclerView() {
        binding.shimmerLayout.apply {
            stopShimmer()
            visibility = View.GONE
        }
        binding.rvNews.visibility = View.VISIBLE
    }

    companion object {
        const val EXTRA_IMAGE_URI = "extra_image_uri"
        const val EXTRA_RESULT = "extra_result"
    }

}