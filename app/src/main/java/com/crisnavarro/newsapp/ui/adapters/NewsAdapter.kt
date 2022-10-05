package com.crisnavarro.newsapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.crisnavarro.newsapp.R
import com.crisnavarro.newsapp.data.models.Article

class NewsAdapter : ListAdapter<Article, NewsAdapter.NewsViewHolder>(NewsDiffCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_article,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
    }

    inner class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    }

    object NewsDiffCallBack : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article) = oldItem.url == newItem.url
        override fun areContentsTheSame(oldItem: Article, newItem: Article) = oldItem == newItem
    }

}