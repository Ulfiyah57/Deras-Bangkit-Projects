package com.deras.id.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.deras.id.databinding.FragmentArticleBinding
import com.deras.id.ui.adapter.ArticleAdapter
import com.deras.id.ui.viewModel.ArticleViewModel

class ArticleFragment : Fragment() {

    private var _binding: FragmentArticleBinding? = null
    private val binding get() = _binding!!
    private val articleViewModel: ArticleViewModel by viewModels()
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
        setupRecyclerView()
        observeViewModel()
        articleViewModel.fetchArticles()
    }

    private fun setupRecyclerView() {
        articleAdapter = ArticleAdapter()
        binding.rvNews.layoutManager = LinearLayoutManager(requireContext())
        binding.rvNews.adapter = articleAdapter
    }

    private fun observeViewModel() {
        articleViewModel.articles.observe(viewLifecycleOwner, Observer { articles ->
            articles?.let {
                articleAdapter.submitList(it)
                binding.shimmerLayout.visibility = View.GONE
                binding.rvNews.visibility = View.VISIBLE
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
