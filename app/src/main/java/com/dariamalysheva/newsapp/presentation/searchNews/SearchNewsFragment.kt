package com.dariamalysheva.newsapp.presentation.searchNews

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dariamalysheva.newsapp.R
import com.dariamalysheva.newsapp.common.utils.constants.Constants
import com.dariamalysheva.newsapp.common.utils.constants.Constants.Companion.BLANK_VALUE
import com.dariamalysheva.newsapp.common.utils.constants.Constants.Companion.DEFAULT_CATEGORY
import com.dariamalysheva.newsapp.common.utils.constants.Constants.Companion.DEFAULT_LANGUAGE
import com.dariamalysheva.newsapp.common.utils.constants.Constants.Companion.DEFAULT_REGION
import com.dariamalysheva.newsapp.common.utils.constants.Constants.Companion.LANGUAGES_MAP
import com.dariamalysheva.newsapp.common.utils.constants.Constants.Companion.PREF_CATEGORY_VALUE
import com.dariamalysheva.newsapp.common.utils.constants.Constants.Companion.PREF_FROM_NETWORK_VALUE
import com.dariamalysheva.newsapp.common.utils.constants.Constants.Companion.PREF_LANGUAGE_VALUE
import com.dariamalysheva.newsapp.common.utils.constants.Constants.Companion.PREF_REGION_VALUE
import com.dariamalysheva.newsapp.common.utils.constants.Constants.Companion.REGIONS_MAP
import com.dariamalysheva.newsapp.common.utils.extensions.navigateToFragment
import com.dariamalysheva.newsapp.databinding.FragmentSearchNewsBinding
import com.dariamalysheva.newsapp.domain.entity.toNewsVO
import com.dariamalysheva.newsapp.presentation.common.dialog.CategoryDialogFragment
import com.dariamalysheva.newsapp.presentation.common.dialog.LanguageDialogFragment
import com.dariamalysheva.newsapp.presentation.common.dialog.RegionDialogFragment
import com.dariamalysheva.newsapp.presentation.common.recyclerview.NewsAdapter
import com.dariamalysheva.newsapp.presentation.newsDetails.NewsDetailsFragment

class SearchNewsFragment : Fragment() {

    private var _binding: FragmentSearchNewsBinding? = null
    private val binding: FragmentSearchNewsBinding
        get() = _binding ?: throw RuntimeException("SearchNewsResultFragment == null")

    private val viewModel by lazy {
        ViewModelProvider(this)[SearchNewsViewModel::class.java]
    }
    private val newsAdapter by lazy {
        NewsAdapter()
    }

    private val preferences: SharedPreferences by lazy {
        requireContext().getSharedPreferences(Constants.APP_PREFERENCES, Context.MODE_PRIVATE)
    }
    private var languageCode: String? = null
    private var regionCode: String? = null
    private var category: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        _binding = FragmentSearchNewsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLanguageCode()
        initRegionCode()
        initCategory()
        setUpRecyclerView()
        setUpClickListeners()
        setUpObservers()
        refreshContent()
        viewModel.getSearchNewsUsingOptions(getOptions(), fromNetwork = getFromNetwork())
        changeFromNetworkStatus()
        setUpDialogListeners()
    }

    private fun getFromNetwork(): Boolean {
        return preferences.getBoolean(PREF_FROM_NETWORK_VALUE, false)
    }

    private fun changeFromNetworkStatus() {
        preferences.edit()
            .putBoolean(PREF_FROM_NETWORK_VALUE, false)
            .apply()
    }

    private fun getOptions(): Map<String, String?> {

        return mapOf(LANGUAGE to languageCode, REGION to regionCode, CATEGORY to category)
    }

    private fun refreshContent() {
        with(binding.swipeRefreshLayout) {
            setOnRefreshListener {
                viewModel.refreshSearchNewsUsingOptions(getOptions(), fromNetwork = true)
                isRefreshing = false
            }
        }
    }

    private fun setUpRecyclerView() {
        with(binding.rvSearchNews) {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setUpClickListeners() {
        newsAdapter.onNewsClickListener = { newsUrl ->
            navigateToFragment(NewsDetailsFragment.newInstance(newsUrl), addToBackStack = true)
        }
        newsAdapter.onLikeClickListener = { liked, newsVoId ->
            if (liked) {
                viewModel.saveNewsToLikeDb(newsVoId)
            } else {
                viewModel.deleteNewsToLikeDb(newsVoId)
            }
        }
    }

    private fun setUpObservers() {
        viewModel.listOfNews.observe(viewLifecycleOwner) { listOfNews ->
            newsAdapter.submitList(listOfNews.map { news ->
                news.toNewsVO()
            })
        }
        viewModel.loading.observe(viewLifecycleOwner) { loading ->
            if (loading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.search_news_bar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.miLanguage -> showLanguageChoiceDialog()
            R.id.miRegion -> showRegionChoiceDialog()
            R.id.miCategory -> showCategoryChoiceDialog()
        }

        return true
    }

    private fun updateList() {
        viewModel.getSearchNewsUsingOptions(getOptions(), fromNetwork = true)
    }

    private fun showLanguageChoiceDialog() {
        val language = getKey(LANGUAGES_MAP, languageCode)
        LanguageDialogFragment.show(requireActivity().supportFragmentManager, language)
    }

    private fun initLanguageCode() {
        if (preferences.getString(PREF_LANGUAGE_VALUE, BLANK_VALUE) == BLANK_VALUE) {
            preferences.edit()
                .putString(PREF_LANGUAGE_VALUE, DEFAULT_LANGUAGE)
                .apply()
        }
        languageCode = LANGUAGES_MAP[preferences.getString(
            PREF_LANGUAGE_VALUE,
            DEFAULT_LANGUAGE
        )].toString()
    }

    private fun initRegionCode() {
        if (preferences.getString(PREF_REGION_VALUE, BLANK_VALUE) == BLANK_VALUE) {
            preferences.edit()
                .putString(PREF_REGION_VALUE, DEFAULT_REGION)
                .apply()
        }
        regionCode = REGIONS_MAP[preferences.getString(
            PREF_REGION_VALUE,
            DEFAULT_REGION
        )].toString()
    }

    private fun initCategory() {
        if (preferences.getString(PREF_CATEGORY_VALUE, BLANK_VALUE) == BLANK_VALUE) {
            preferences.edit()
                .putString(PREF_CATEGORY_VALUE, DEFAULT_CATEGORY)
                .apply()
        }
        category = preferences.getString(PREF_CATEGORY_VALUE, DEFAULT_CATEGORY).toString()
    }

    private fun setUpDialogListeners() {
        setUpLanguageChoiceDialogListener()
        setUpRegionChoiceDialogListener()
        setUpCategoryChoiceDialogListener()
    }

    private fun setUpLanguageChoiceDialogListener() {
        LanguageDialogFragment.setUpListener(
            requireActivity().supportFragmentManager,
            viewLifecycleOwner
        ) { selectedLanguage ->
            preferences.edit()
                .putString(PREF_LANGUAGE_VALUE, selectedLanguage)
                .apply()
            initLanguageCode()
            updateList()
        }
    }

    private fun showRegionChoiceDialog() {
        val region = getKey(REGIONS_MAP, regionCode)
        RegionDialogFragment.show(requireActivity().supportFragmentManager, region)
    }

    private fun setUpRegionChoiceDialogListener() {
        RegionDialogFragment.setUpListener(
            requireActivity().supportFragmentManager,
            viewLifecycleOwner
        ) { selectedRegion ->
            preferences.edit()
                .putString(PREF_REGION_VALUE, selectedRegion)
                .apply()
            initRegionCode()
            updateList()
        }
    }

    private fun showCategoryChoiceDialog() {
        CategoryDialogFragment.show(requireActivity().supportFragmentManager, category)
    }

    private fun setUpCategoryChoiceDialogListener() {
        CategoryDialogFragment.setUpListener(
            requireActivity().supportFragmentManager,
            viewLifecycleOwner
        ) { selectedCategory ->
            preferences.edit()
                .putString(PREF_CATEGORY_VALUE, selectedCategory)
                .apply()
            initCategory()
            updateList()
        }
    }

    private fun getKey(map: Map<String, String>, target: String?): String {

        return map.filter { target == it.value }.keys.first()
    }

    companion object {

        private const val LANGUAGE = "language"
        private const val REGION = "country"
        private const val CATEGORY = "category"
    }
}