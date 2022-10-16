package com.crisnavarro.newsapp.ui.searchnews.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.crisnavarro.newsapp.R
import com.crisnavarro.newsapp.core.hideKeyboard
import com.crisnavarro.newsapp.core.showKeyboard
import com.crisnavarro.newsapp.data.models.Article
import com.crisnavarro.newsapp.databinding.FragmentSearchNewsBinding
import com.crisnavarro.newsapp.ui.adapters.NewsAdapter
import com.crisnavarro.newsapp.ui.searchnews.viewmodel.SearchViewModel


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