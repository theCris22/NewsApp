package com.crisnavarro.newsapp.ui.article.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.crisnavarro.newsapp.R
import com.crisnavarro.newsapp.core.hide
import com.crisnavarro.newsapp.core.shortSnackBar
import com.crisnavarro.newsapp.core.show
import com.crisnavarro.newsapp.data.network.models.Article
import com.crisnavarro.newsapp.databinding.FragmentArticleBinding
import com.crisnavarro.newsapp.ui.article.viewmodel.ArticleViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleFragment : Fragment(R.layout.fragment_article) {

    private val viewModel: ArticleViewModel by viewModels()
    private val args: ArticleFragmentArgs by navArgs()
    private lateinit var binding: FragmentArticleBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentArticleBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(args.article)
    }


    private fun initViews(article: Article) {
        with(binding) {

            wvArticle.webViewClient = WebClient()
            wvArticle.loadUrl(article.url.toString())

            fabSave.setOnClickListener {
                viewModel.saveArticle(article)
                it.shortSnackBar(getString(R.string.article_added_successfully))
            }
        }

    }

    override fun onStart() {
        super.onStart()
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation_view).hide()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    override fun onDestroy() {
        super.onDestroy()
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation_view).show()
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
    }

    inner class WebClient : WebViewClient() {

        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            view?.loadUrl(request?.url.toString())
            return false
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            binding.pbLoading.hide()
        }

    }

}