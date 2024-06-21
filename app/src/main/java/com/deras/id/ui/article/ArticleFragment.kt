package com.deras.id.ui.article

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.deras.id.databinding.FragmentArticleBinding
import com.deras.id.ui.viewModel.ArticleViewModel

class ArticleFragment : Fragment() {

    private var _binding: FragmentArticleBinding? = null
    private val binding get() = _binding!!

    private lateinit var articleViewModel: ArticleViewModel
    private lateinit var articleAdapter: ArticleAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArticleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        articleViewModel = ViewModelProvider(this)[ArticleViewModel::class.java]
        articleAdapter = ArticleAdapter()

        binding.rvAllnews.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = articleAdapter
        }

        binding.swipeRefresh.setOnRefreshListener {
            articleViewModel.fetchAllNews()
        }

        observeViewModel()
        articleViewModel.fetchAllNews()
    }

    private fun observeViewModel() {
        articleViewModel.articles.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ResultState.Loading -> {
                    binding.swipeRefresh.isRefreshing = true
                }

                is ResultState.Success -> {
                    binding.swipeRefresh.isRefreshing = false
                    articleAdapter.submitList(result.data)
                }

                is ResultState.Error -> {
                    binding.swipeRefresh.isRefreshing = false
                    Toast.makeText(requireContext(), "Error: ${result.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}