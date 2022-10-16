package com.crisnavarro.newsapp.ui.article.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.crisnavarro.newsapp.R
import com.crisnavarro.newsapp.core.shortSnackBar
import com.crisnavarro.newsapp.data.models.Article
import com.crisnavarro.newsapp.databinding.FragmentArticleBinding
import com.crisnavarro.newsapp.ui.article.viewmodel.ArticleViewModel

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

            wvArticle.apply {
                webViewClient = WebViewClient()
                loadUrl(article.url.toString())
            }

            fabSave.setOnClickListener {
                viewModel.saveArticle(requireContext(), article)
                it.shortSnackBar(getString(R.string.article_added_successfully))
            }
        }

    }

    override fun onStart() {
        super.onStart()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
    }

}