package com.dariamalysheva.newsapp.data.database.room.dao

import androidx.room.*
import com.dariamalysheva.newsapp.data.database.room.entities.LatestNewsDB
import com.dariamalysheva.newsapp.data.database.room.entities.LikedNewsDb
import com.dariamalysheva.newsapp.data.database.room.entities.SearchNewsDB

@Dao
interface NewsDao {

    @Query("SELECT * FROM latestNews WHERE `id` = :id")
    suspend fun getLatestNewsById(id: String): LatestNewsDB

    @Query("SELECT * FROM latestNews")
    suspend fun getAllLatestNews(): List<LatestNewsDB>

    @Insert
    suspend fun insertAllToLatestNews(news: List<LatestNewsDB>)

    @Insert
    suspend fun insertOneNewsToLatest(news: LatestNewsDB)

    @Query("DELETE FROM latestNews")
    suspend fun deleteAllLatestNews()

    @Update
    suspend fun updateLatestNews(news: LatestNewsDB)

    @Query("SELECT * FROM likedNews")
    suspend fun getAllLikedNews(): List<LikedNewsDb>

    @Query("SELECT * FROM likedNews WHERE `id` = :id")
    suspend fun getLikedNewsById(id: String): LikedNewsDb?

    @Insert
    suspend fun insertOneNewsToLiked(news: LikedNewsDb)

    @Query("DELETE FROM likedNews WHERE `id`=:newsId")
    suspend fun deleteNewsFromLikedById(newsId: String)

    @Query("SELECT * FROM searchNews WHERE `id` = :id")
    suspend fun getSearchNewsById(id: String): SearchNewsDB

    @Query("SELECT * FROM searchNews")
    suspend fun getAllSearchNews(): List<SearchNewsDB>

    @Insert
    suspend fun insertAllToSearchNews(news: List<SearchNewsDB>)

    @Insert
    suspend fun insertOneNewsToSearch(news: SearchNewsDB)

    @Query("DELETE FROM searchNews")
    suspend fun deleteAllSearchNews()

    @Update
    suspend fun updateSearchNews(news: SearchNewsDB)

    @Query("SELECT EXISTS(SELECT * FROM searchNews WHERE id = :id)")
    fun existsInSearchNews(id: String): Boolean

    @Query("SELECT EXISTS(SELECT * FROM latestNews WHERE id = :id)")
    fun existsInLatestNews(id: String): Boolean
}