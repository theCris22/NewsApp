package com.crisnavarro.newsapp.ui.adapters

import android.view.LayoutInflater
import android.view.RoundedCorner
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.crisnavarro.newsapp.R
import com.crisnavarro.newsapp.data.models.Article
import com.crisnavarro.newsapp.databinding.ItemArticleBinding

class NewsAdapter(private val onItemClickListener: (article: Article) -> Unit) :
    ListAdapter<Article, NewsAdapter.NewsViewHolder>(NewsDiffCallBack) {

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
        holder.bindingData(currentList[position], onItemClickListener)
    }

    inner class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = ItemArticleBinding.bind(view)

        fun bindingData(article: Article, onItemClickListener: (article: Article) -> Unit) {
            with(binding) {

                Glide.with(itemView).load(article.urlToImage)
                    .apply(RequestOptions.bitmapTransform(RoundedCorners(20)))
                    .placeholder(R.drawable.icon_news).into(ivArticle)
                tvTitleArticle.text = article.title
                tvDateArticle.text = article.publishedAt?.take(10)
                rowArticle.setOnClickListener { onItemClickListener(article) }
            }
        }

    }

    object NewsDiffCallBack : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article) = oldItem.url == newItem.url

        override fun areContentsTheSame(oldItem: Article, newItem: Article) = oldItem == newItem
    }

}