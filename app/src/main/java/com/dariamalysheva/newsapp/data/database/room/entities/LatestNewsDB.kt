package com.dariamalysheva.newsapp.data.database.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dariamalysheva.newsapp.domain.entity.News

@Entity(tableName = "latestNews")
data class LatestNewsDB(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "key")
    val key: Long? = null,

    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "author")
    val author: String,

    @ColumnInfo(name = "category")
    val category: List<String>,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "image")
    val image: String,

    @ColumnInfo(name = "language")
    val language: String,

    @ColumnInfo(name = "published")
    val published: String,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "url")
    val url: String,

    @ColumnInfo(name = "liked")
    var liked: Boolean
)

fun LatestNewsDB.toNews(): News {

    return News(author, category, description, id, image, language, published, title, url, liked)
}

fun LatestNewsDB.toLikedNewsDB(): LikedNewsDb {

    return LikedNewsDb(
        key, id, author, category, description, image, language, published, title, url)
}
