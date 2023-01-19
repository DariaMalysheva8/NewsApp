package com.dariamalysheva.newsapp.data.database.room.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dariamalysheva.newsapp.data.database.room.Converters
import com.dariamalysheva.newsapp.data.database.room.dao.NewsDao
import com.dariamalysheva.newsapp.data.database.room.entities.*

@Database(entities = [LatestNewsDB::class, LikedNewsDb::class, SearchNewsDB::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getNewsDao(): NewsDao

    companion object {

        private const val DATABASE_NAME = "news_database"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(application: Application): AppDatabase {

            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    application,
                    AppDatabase::class.java,
                    DATABASE_NAME
                )
                    .build()
                INSTANCE = instance

                instance
            }
        }

    }
}