package com.crisnavarro.newsapp.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavHost
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.crisnavarro.newsapp.R
import com.crisnavarro.newsapp.databinding.ActivityNewsBinding

class NewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()

    }

    private fun initViews() {
        val navHost =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHost
        val navController = navHost.navController

        val appBarConfig = AppBarConfiguration(
            setOf(R.id.breakingNewsFragment, R.id.savedNewsFragment, R.id.searchNewsFragment)
        )

        setupActionBarWithNavController(
            navController,
            appBarConfig
        )

        with(binding) {
            bottomNavigationView.setupWithNavController(navController)
        }
    }

}