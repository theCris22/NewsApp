package com.crisnavarro.newsapp.ui.searchnews.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.crisnavarro.newsapp.R
import com.crisnavarro.newsapp.core.hide
import com.crisnavarro.newsapp.core.show
import com.crisnavarro.newsapp.data.network.models.Article
import com.crisnavarro.newsapp.databinding.FragmentSearchNewsBinding
import com.crisnavarro.newsapp.ui.adapters.NewsAdapter
import com.crisnavarro.newsapp.ui.searchnews.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchNewsFragment : Fragment(R.layout.fragment_search_news) {

    private val viewModel: SearchViewModel by viewModels()
    private lateinit var binding: FragmentSearchNewsBinding
    private lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        initViews()
    }

    private fun initObservers() {
        viewModel.searchResult.observe(viewLifecycleOwner) {
            newsAdapter.submitList(it.articles)
        }
        viewModel.loading.observe(viewLifecycleOwner) {
            binding.srlNews.isRefreshing = it
        }
    }

    private fun initViews() {
        newsAdapter = NewsAdapter { goToArticle(it) }

        with(binding) {

            svNews.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(text: String?): Boolean {
                    viewModel.searchNews(text ?: "")
                    svNews.clearFocus()
                    return true
                }

                override fun onQueryTextChange(text: String?) = true

            })

            rvNews.apply {
                adapter = newsAdapter
                setHasFixedSize(true)
            }

            srlNews.setOnRefreshListener {
                if (svNews.query.toString().isNotEmpty()) {
                    viewModel.searchNews(svNews.query.toString() ?: "")
                    svNews.clearFocus()
                } else srlNews.isRefreshing = false
            }

        }
    }

    private fun goToArticle(article: Article) {
        val bundle = Bundle().apply {
            putSerializable("article", article)
        }
        findNavController().navigate(
            R.id.action_searchNewsFragment_to_articleFragment,
            bundle
        )
    }
}