package com.deras.id.ui.article

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.deras.id.databinding.ArticleCardBinding
import com.deras.id.response.ArticlesItem

class ArticleAdapter : ListAdapter<ArticlesItem, ArticleAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ArticleCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val article = getItem(position)
        holder.bind(article)
    }

    class MyViewHolder(private val binding: ArticleCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(article: ArticlesItem) {
            binding.newsTitle.text = article.title
            binding.newsDescription.text = article.description
            if (article.urlToImage != null) {
                Glide.with(binding.newsImage.context)
                    .load(article.urlToImage)
                    .into(binding.newsImage)
            }

            binding.root.setOnClickListener {
                val intentUrl = Intent(Intent.ACTION_VIEW, Uri.parse(article.url))
                binding.root.context.startActivity(intentUrl)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ArticlesItem>() {
            override fun areItemsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean {
                return oldItem.url == newItem.url
            }
            override fun areContentsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}