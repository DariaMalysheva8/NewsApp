package com.dariamalysheva.newsapp.presentation.latestNews

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dariamalysheva.newsapp.R
import com.dariamalysheva.newsapp.common.utils.constants.Constants.Companion.APP_PREFERENCES
import com.dariamalysheva.newsapp.common.utils.constants.Constants.Companion.BLANK_VALUE
import com.dariamalysheva.newsapp.common.utils.constants.Constants.Companion.DEFAULT_LANGUAGE
import com.dariamalysheva.newsapp.common.utils.constants.Constants.Companion.DEFAULT_REGION
import com.dariamalysheva.newsapp.common.utils.constants.Constants.Companion.LANGUAGES_MAP
import com.dariamalysheva.newsapp.common.utils.constants.Constants.Companion.PREF_LANGUAGE_VALUE
import com.dariamalysheva.newsapp.common.utils.constants.Constants.Companion.PREF_REGION_VALUE
import com.dariamalysheva.newsapp.common.utils.constants.Constants.Companion.REGIONS_MAP
import com.dariamalysheva.newsapp.common.utils.extensions.navigateToFragment
import com.dariamalysheva.newsapp.databinding.FragmentLatestNewsBinding
import com.dariamalysheva.newsapp.domain.entity.toNewsVO
import com.dariamalysheva.newsapp.presentation.common.dialog.LanguageDialogFragment
import com.dariamalysheva.newsapp.presentation.common.dialog.RegionDialogFragment
import com.dariamalysheva.newsapp.presentation.common.recyclerview.NewsAdapter
import com.dariamalysheva.newsapp.presentation.newsDetails.NewsDetailsFragment

class LatestNewsFragment : Fragment() {

    private var _binding: FragmentLatestNewsBinding? = null
    private val binding: FragmentLatestNewsBinding
        get() = _binding ?: throw RuntimeException("LatestNewsFragment == null")

    private val newsAdapter by lazy(LazyThreadSafetyMode.NONE) {
        NewsAdapter()
    }

    private val viewModel: LatestNewsViewModel by lazy {
        ViewModelProvider(this)[LatestNewsViewModel::class.java]
    }

    private val preferences: SharedPreferences by lazy {
        requireContext().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
    }
    private var languageCode: String? = null
    private var regionCode: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        _binding = FragmentLatestNewsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLanguageCode()
        initRegionCode()
        val fromNetwork = arguments?.getBoolean(FROM_NETWORK, true)
        updateList(fromNetwork)
        setUpRecyclerView()
        setUpClickListeners()
        setUpObservers()
        refreshContent()
        setUpDialogListeners()
    }

    private fun updateList(fromNetwork: Boolean?) {
        viewModel.getLatestNews(languageCode, regionCode, fromNetwork)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.latest_news_bar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.miLanguage -> showLanguageChoiceDialog()
            R.id.miRegion -> showRegionChoiceDialog()
        }

        return true
    }

    private fun setUpRecyclerView() {
        with(binding.rvLatestNews) {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun refreshContent() {
        with(binding.swipeRefreshLayout) {
            setOnRefreshListener {
                viewModel.refreshLatestNews(languageCode, regionCode, true)
                isRefreshing = false
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
        languageCode =
            LANGUAGES_MAP[preferences.getString(PREF_LANGUAGE_VALUE, DEFAULT_LANGUAGE)].toString()
    }

    private fun initRegionCode() {
        if (preferences.getString(PREF_REGION_VALUE, BLANK_VALUE) == BLANK_VALUE) {
            preferences.edit()
                .putString(PREF_REGION_VALUE, DEFAULT_REGION)
                .apply()
        }
        regionCode =
            REGIONS_MAP[preferences.getString(PREF_REGION_VALUE, DEFAULT_REGION)].toString()
    }

    private fun setUpDialogListeners() {
        setUpLanguageChoiceDialogListener()
        setUpRegionChoiceDialogListener()
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
            updateList(true)
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
            updateList(true)
        }
    }

    private fun getKey(map: Map<String, String>, target: String?): String {
        return map.filter { target == it.value }.keys.first()
    }

    companion object {

        private const val FROM_NETWORK = "FROM_NETWORK"

        fun newInstance(fromNetwork: Boolean) =
            LatestNewsFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(FROM_NETWORK, fromNetwork)
                }
            }
    }
}