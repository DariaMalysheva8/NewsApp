package com.dariamalysheva.newsapp.data.database.room

import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun toListOfStrings(flatStringList: String): List<String> {

        return flatStringList.split(",")
    }

    @TypeConverter
    fun fromListOfStrings(listOfString: List<String>): String {

        return listOfString.joinToString(separator = ",")
    }
}