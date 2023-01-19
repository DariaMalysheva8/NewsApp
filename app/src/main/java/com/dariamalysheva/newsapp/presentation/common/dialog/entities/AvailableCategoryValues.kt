package com.dariamalysheva.newsapp.presentation.common.dialog.entities

import com.dariamalysheva.newsapp.common.utils.constants.Constants

class AvailableCategoryValues(
    val category: List<String>,
    val currentIndex: Int
) {

    companion object {

        fun createCategoryValues(currentCategory: String?): AvailableCategoryValues {
            val categories = Constants.CATEGORIES_LIST
            val currentIndex = categories.indexOf(currentCategory)

            return AvailableCategoryValues(categories, currentIndex)
        }
    }
}