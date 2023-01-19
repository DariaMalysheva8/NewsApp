package com.dariamalysheva.newsapp.presentation.common.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dariamalysheva.newsapp.R
import com.dariamalysheva.newsapp.databinding.NewsRecyclerViewItemBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsAdapter : ListAdapter<NewsVO, NewsAdapter.NewsResultViewHolder>(NewsDiffCallback()) {

    var onNewsClickListener: ((String) -> Unit)? = null
    var onLikeClickListener: ((Boolean, String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsResultViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = NewsRecyclerViewItemBinding.inflate(inflater, parent, false)

        return NewsResultViewHolder(binding, onLikeClickListener)
    }

    override fun onBindViewHolder(holder: NewsResultViewHolder, position: Int) {
        val news = getItem(position)
        holder.bind(news)
        holder.itemView.setOnClickListener {
            onNewsClickListener?.invoke(news.url)
        }
    }

    class NewsResultViewHolder(
        private val binding: NewsRecyclerViewItemBinding,
        private val onLikeClickListener: ((Boolean, String) -> Unit)?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(newsVO: NewsVO) {
            with(binding) {
                tvDatePublished.text = newsVO.published.substring(0, 16)
                tvArticleTitle.text = newsVO.title
                tvArticleDescription.text = newsVO.description
                if (newsVO.imageUrl == VALUE_IF_NO_IMAGE) {
                    ivNewsImage.visibility = View.GONE
                } else {
                    ivNewsImage.visibility = View.VISIBLE
                    CoroutineScope(Dispatchers.Main).launch {
                        Glide.with(binding.root)
                            .load(newsVO.imageUrl)
                            .placeholder(R.drawable.ic_launcher_foreground)
                            .error(R.drawable.ic_launcher_foreground)
                            .into(ivNewsImage)
                    }
                }
                setBackgroundForLikedIcon(likedStatus = newsVO.liked, btnLike = binding.btnLike)
                btnLike.setOnClickListener { btnLike ->
                    changeLikeStatus(newsVO)
                    onLikeClickListener?.let { onLickClick ->
                        onLickClick(newsVO.liked, newsVO.id)
                    }
                }
            }
        }

        private fun changeLikeStatus(newsVO: NewsVO) {
            newsVO.liked = !newsVO.liked
            setBackgroundForLikedIcon(likedStatus = newsVO.liked, btnLike = binding.btnLike)
        }

        private fun setBackgroundForLikedIcon(likedStatus: Boolean, btnLike: Button) {
            if (likedStatus) {
                btnLike.setBackgroundResource(R.drawable.ic_like_red)
            } else {
                btnLike.setBackgroundResource(R.drawable.ic_like_red_border)
            }
        }

        companion object {
            private const val VALUE_IF_NO_IMAGE = "None"
        }
    }
}