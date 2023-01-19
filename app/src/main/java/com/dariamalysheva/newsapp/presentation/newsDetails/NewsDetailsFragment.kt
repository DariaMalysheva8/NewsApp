package com.dariamalysheva.newsapp.presentation.newsDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import com.dariamalysheva.newsapp.databinding.FragmentNewsDetailsBinding

class NewsDetailsFragment : Fragment() {

    private var _binding: FragmentNewsDetailsBinding? = null
    private val binding: FragmentNewsDetailsBinding
        get() = _binding ?: throw RuntimeException("NewsDetailsFragment == null")

    private lateinit var newsUrl: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsDetailsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parseArgs()
        setWebView()
    }

    private fun setWebView() {
        with(binding.wbvNews) {
            webViewClient = WebViewClient()
            loadUrl(newsUrl)
            settings.javaScriptEnabled = true
        }
    }

    private fun parseArgs() {
        val args = requireArguments()
        if (!args.containsKey(NEWS_URL)) {
            throw RuntimeException("Param news url is absent")
        }
        newsUrl = args.getString(NEWS_URL).toString()
    }

    companion object {

        private const val NEWS_URL = "NEWS_URL"

        fun newInstance(newsUrl: String): NewsDetailsFragment {
            return NewsDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(NEWS_URL, newsUrl)
                }
            }
        }
    }
}