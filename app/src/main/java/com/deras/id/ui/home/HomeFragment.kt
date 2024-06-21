package com.deras.id.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.deras.id.databinding.FragmentHomeBinding
import com.deras.id.ui.article.ArticleAdapter
import com.deras.id.ui.viewModel.ArticleViewModel
import com.deras.id.ui.article.ResultState

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var articleViewModel: ArticleViewModel
    private lateinit var articleAdapter: ArticleAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        articleViewModel = ViewModelProvider(this)[ArticleViewModel::class.java]
        articleAdapter = ArticleAdapter()

        binding.rvNews.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = articleAdapter
        }

        binding.swipeRefresh.setOnRefreshListener {
            articleViewModel.fetchArticles()
        }

        observeViewModel()
        articleViewModel.fetchArticles()
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
                    // Handle error state, e.g., show an error message
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}