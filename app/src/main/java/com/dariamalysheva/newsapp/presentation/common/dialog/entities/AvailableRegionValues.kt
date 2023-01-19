package com.dariamalysheva.newsapp.presentation.common.dialog.entities

import com.dariamalysheva.newsapp.common.utils.constants.Constants.Companion.REGIONS_MAP

class AvailableRegionValues(
    val regions: List<String>,
    val currentIndex: Int
) {

    companion object {

        fun createRegionValues(currentRegion: String?): AvailableRegionValues {
            val regions = REGIONS_MAP.keys.toList()
            val currentIndex = regions.indexOf(currentRegion)

            return AvailableRegionValues(regions, currentIndex)
        }
    }
}