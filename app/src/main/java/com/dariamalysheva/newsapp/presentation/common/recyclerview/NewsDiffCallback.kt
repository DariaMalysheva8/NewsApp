package com.dariamalysheva.newsapp.presentation.common.recyclerview

import androidx.recyclerview.widget.DiffUtil

class NewsDiffCallback : DiffUtil.ItemCallback<NewsVO>() {

    override fun areItemsTheSame(oldItem: NewsVO, newItem: NewsVO): Boolean {

        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: NewsVO, newItem: NewsVO): Boolean {

        return oldItem == newItem
    }
}