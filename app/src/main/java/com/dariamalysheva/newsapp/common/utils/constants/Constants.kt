package com.dariamalysheva.newsapp.common.utils.constants

class Constants {

    companion object {
        const val API_KEY = "9vAQzHK99RDRBQfbccWbaq8PcIehw2eLFDP2g-gNrP9aYOlF"

        const val APP_PREFERENCES = "APP_PREFERENCES"
        const val PREF_FROM_NETWORK_VALUE = "PREF_FROM_NETWORK_VALUE"
        const val PREF_LANGUAGE_VALUE = "PREF_LANGUAGE_VALUE"
        const val PREF_REGION_VALUE = "PREF_REGION_VALUE"
        const val PREF_CATEGORY_VALUE = "PREF_CATEGORY_VALUE"

        const val DEFAULT_LANGUAGE = "English"
        const val DEFAULT_REGION = "United State"
        const val DEFAULT_CATEGORY = "politics"
        const val BLANK_VALUE = ""

        val LANGUAGES_MAP = mapOf(
            "Arabic" to "ar", "Chinese" to "zh", "Dutch" to "nl", "English" to "en",
            "Finnish" to "fi", "French" to "fr", "German" to "de", "Hindi" to "hi",
            "Italian" to "it", "Japanese" to "ja", "Korean" to "ko", "Malay" to "msa",
            "Portuguese" to "pt", "Russian" to "ru", "Spanish" to "es", "Vietnamese" to "vi",
            "Danish" to "da", "Czech" to "cs", "Greek" to "el", "Hungarian" to "hu",
            "Serbian" to "sr", "Thai" to "th", "Turkish" to "tr"
        )

        val REGIONS_MAP = mapOf(
            "Afghanistan" to "AF", "Argentina" to "AR", "Asia" to "ASIA", "Australia" to "AU",
            "Austria" to "AT", "Bangladesh" to "BD", "Belgium" to "BE", "Bolivia" to "BO",
            "Bosnia" to "BA", "Brazil" to "BR", "Cambodia" to "KH", "Canada" to "CA",
            "Chile" to "CL", "China" to "CN", "Colombia" to "CO", "Czech Republic" to "CZ",
            "Denmark" to "DK", "Ecuador" to "EC", "Egypt" to "EG", "Estonia" to "EE",
            "Europe" to "EU", "Finland" to "FI", "France" to "FR", "German" to "DE",
            "Ghana" to "GH", "Greece" to "GR", "Hong Kong" to "HK", "Hungary" to "HU",
            "India" to "IN", "Indonesia" to "ID", "International" to "INT", "Iran" to "IR",
            "Iraq" to "IQ", "Ireland" to "IE", "Israel" to "IL", "Italy" to "IT", "Japan" to "JP",
            "Kenya" to "KE", "Kuwait" to "KW", "Lebanon" to "LB", "Luxembourg" to "LU",
            "Malaysia" to "MY", "Mexico" to "MX", "Myanmar" to "MM", "Nepal" to "NP",
            "Netherlands" to "NL", "New Zealand" to "NZ", "Nigeria" to "NG", "North Korea" to "NK",
            "Norway" to "NO", "Pakistan" to "PK", "Palestine" to "PS", "Panama" to "PA",
            "Paraguay" to "PY", "Peru" to "PE", "Philippines" to "PH", "Poland" to "PL",
            "Portugal" to "PT", "Qatar" to "QA", "Romania" to "RO", "Russia" to "RU",
            "Saudi-Arabia" to "SA", "Serbia" to "RS", "Singapore" to "SG", "Slovenia" to "SI",
            "South Korea" to "KR", "Spain" to "ES", "Sweden" to "SE", "Switzerland" to "CH",
            "Taiwan" to "TW", "Thailand" to "TH", "Turkey" to "TR", "United Arab Emirates" to "AE",
            "United Kingdom" to "GB", "United State" to "US", "Uruguay" to "UY",
            "Venezuela" to "VE", "Vietnam" to "VN", "Zimbabwe" to "ZW"
        )

        val CATEGORIES_LIST = listOf(
            "academia", "business", "entertainment", "finance", "food", "game", "general",
            "health", "lifestyle", "opinion", "politics", "programming", "regional", "science",
            "sports", "technology", "world"
        )
    }
}