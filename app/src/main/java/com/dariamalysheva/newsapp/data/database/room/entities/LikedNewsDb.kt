package com.dariamalysheva.newsapp.data.database.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dariamalysheva.newsapp.domain.entity.News

@Entity(tableName = "likedNews")
data class LikedNewsDb(
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
    val url: String
)

fun LikedNewsDb.toNews(): News {

    return News(
        author, category, description, id, image, language, published, title, url, liked = true)
}