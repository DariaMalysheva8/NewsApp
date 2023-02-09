package com.dariamalysheva.newsapp.presentation.likedNews

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dariamalysheva.newsapp.common.utils.extensions.navigateToFragment
import com.dariamalysheva.newsapp.databinding.FragmentLikedNewsBinding
import com.dariamalysheva.newsapp.domain.entity.toNewsVO
import com.dariamalysheva.newsapp.presentation.NewsApp
import com.dariamalysheva.newsapp.presentation.common.ViewModelFactory
import com.dariamalysheva.newsapp.presentation.common.recyclerview.NewsAdapter
import com.dariamalysheva.newsapp.presentation.newsDetails.NewsDetailsFragment
import javax.inject.Inject

class LikedNewsFragment : Fragment() {

    private var _binding: FragmentLikedNewsBinding? = null
    private val binding: FragmentLikedNewsBinding
        get() = _binding ?: throw RuntimeException("LikedNewsFragment == null")

    private val newsAdapter by lazy {
        NewsAdapter()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: LikedNewsViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[LikedNewsViewModel::class.java]
    }

    private val component by lazy {
        (requireActivity().application as NewsApp).component
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLikedNewsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        setUpObservers()
        setUpSwipeListener(binding.rvLikedNews)
        viewModel.getLikedNewsFromDb()
        setUpClickListeners()
    }

    private fun setUpObservers() {
        viewModel.listOfLikedNews.observe(viewLifecycleOwner) { listOfNews ->
            newsAdapter.submitList(listOfNews.map { news ->
                news.toNewsVO()
            })
        }
    }

    private fun setUpRecyclerView() {
        with(binding.rvLikedNews) {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setUpSwipeListener(rvLikedNews: RecyclerView) {
        val callback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val news = newsAdapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteNewsToLikeDb(news.id)
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rvLikedNews)
    }

    private fun setUpClickListeners() {
        newsAdapter.onNewsClickListener = { newsUrl ->
            navigateToFragment(NewsDetailsFragment.newInstance(newsUrl), addToBackStack = true)
        }

        newsAdapter.onLikeClickListener = { _, newsVoId ->
            viewModel.deleteNewsToLikeDb(newsVoId)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}