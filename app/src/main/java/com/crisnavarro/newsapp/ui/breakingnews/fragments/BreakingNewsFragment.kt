package com.crisnavarro.newsapp.ui.breakingnews.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.crisnavarro.newsapp.R
import com.crisnavarro.newsapp.core.hide
import com.crisnavarro.newsapp.core.show
import com.crisnavarro.newsapp.data.models.Article
import com.crisnavarro.newsapp.databinding.FragmentBreakingNewsBinding
import com.crisnavarro.newsapp.ui.adapters.NewsAdapter
import com.crisnavarro.newsapp.ui.breakingnews.viewmodel.BreakingNewsViewModel

class BreakingNewsFragment : Fragment(R.layout.fragment_breaking_news) {

    private val viewModel: BreakingNewsViewModel by viewModels()
    private lateinit var binding: FragmentBreakingNewsBinding
    private lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentBreakingNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserves()
        initViews()

    }

    private fun initObserves() {
        viewModel.news.observe(viewLifecycleOwner) {
            newsAdapter.submitList(it.articles)
        }
        viewModel.loading.observe(viewLifecycleOwner) {
            if (it)
                binding.loading.show()
            else
                binding.loading.hide()
        }
    }

    private fun initViews() {
        newsAdapter = NewsAdapter { goToArticle(it) }

        with(binding) {
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
            R.id.action_breakingNewsFragment_to_articleFragment,
            bundle
        )
    }

}