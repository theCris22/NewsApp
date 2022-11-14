package com.crisnavarro.newsapp.ui.savednews.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.crisnavarro.newsapp.R
import com.crisnavarro.newsapp.core.hide
import com.crisnavarro.newsapp.core.shortSnackBar
import com.crisnavarro.newsapp.core.show
import com.crisnavarro.newsapp.data.network.models.Article
import com.crisnavarro.newsapp.databinding.FragmentSavedNewsBinding
import com.crisnavarro.newsapp.ui.adapters.NewsAdapter
import com.crisnavarro.newsapp.ui.savednews.viewmodel.SavedNewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SavedNewsFragment : Fragment(R.layout.fragment_saved_news) {

    private val viewModel: SavedNewsViewModel by viewModels()
    private lateinit var binding: FragmentSavedNewsBinding
    private lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSavedNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserves()
        initViews()
    }

    private fun initViews() {
        newsAdapter = NewsAdapter { deleteNew(it) }
        with(binding) {
            rvNews.apply {
                adapter = newsAdapter
                setHasFixedSize(true)
            }

            srlNews.setOnRefreshListener {
                viewModel.getSavedNews()
                srlNews.isRefreshing = false
            }

            val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP or ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            ) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return true
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition
                    val article = newsAdapter.currentList[position]
                    deleteNew(article)
                    view?.shortSnackBar(getString(R.string.article_deleted_successfully))
                }
            }

            ItemTouchHelper(itemTouchHelperCallback).apply {
                attachToRecyclerView(rvNews)
            }

        }

    }

    private fun initObserves() {
        viewModel.getSavedNews().observe(viewLifecycleOwner) {
            newsAdapter.submitList(it)
            validateList(it ?: listOf())
        }
    }

    private fun validateList(list: List<Article>) {
        with(binding) {
            if (list.isNotEmpty()) {
                rvNews.show()
                lyEmpty.root.hide()
            } else {
                rvNews.hide()
                lyEmpty.tvEmptyText.text = getString(R.string.app_not_saved_news_yet)
                lyEmpty.root.show()
            }
        }
    }

    private fun deleteNew(article: Article) = viewModel.deleteNew(article)

}