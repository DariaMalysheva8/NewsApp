package com.dariamalysheva.newsapp.presentation.common.dialog.entities

import com.dariamalysheva.newsapp.common.utils.constants.Constants.Companion.LANGUAGES_MAP

class AvailableLanguageValues(
    val languages: List<String>,
    val currentIndex: Int
) {

    companion object {

        fun createLanguageValues(currentLanguage: String?): AvailableLanguageValues {
            val languages = LANGUAGES_MAP.keys.toList()
            val currentIndex = languages.indexOf(currentLanguage)

            return AvailableLanguageValues(languages, currentIndex)
        }
    }
}