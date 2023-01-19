package com.dariamalysheva.newsapp.presentation

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.dariamalysheva.newsapp.R
import com.dariamalysheva.newsapp.common.utils.constants.Constants
import com.dariamalysheva.newsapp.databinding.ActivityMainBinding
import com.dariamalysheva.newsapp.presentation.latestNews.LatestNewsFragment
import com.dariamalysheva.newsapp.presentation.likedNews.LikedNewsFragment
import com.dariamalysheva.newsapp.presentation.searchNews.SearchNewsFragment

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding
        get() = _binding ?: throw RuntimeException("MainActivity == null")

    private val preferences: SharedPreferences by lazy {
        getSharedPreferences(Constants.APP_PREFERENCES, Context.MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            preferences.edit()
                .putBoolean(Constants.PREF_FROM_NETWORK_VALUE, true)
                .apply()
            navigateToFragment(LatestNewsFragment.newInstance(true))
        }

        setUpBottomNavMenuListener()
    }

    private fun setUpBottomNavMenuListener() {
        binding.bottomNavMenu.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.miLatestNews -> navigateToFragment(LatestNewsFragment.newInstance(false))
                R.id.miSearch -> navigateToFragment(SearchNewsFragment())
                R.id.miLikes -> navigateToFragment(LikedNewsFragment())
            }
            true
        }
    }

    private fun navigateToFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer, fragment)
            commit()
        }
    }
}