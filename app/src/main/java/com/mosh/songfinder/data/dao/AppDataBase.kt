package com.mosh.songfinder.data.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mosh.songfinder.data.dao.entity.SearchEntity
import com.mosh.songfinder.data.dao.entity.SongEntity

@Database(
    version = 1,
    entities = [SongEntity::class, SearchEntity::class]
)
abstract class AppDataBase: RoomDatabase() {

    abstract fun getSongDao(): SongDao
    abstract fun getSearchDao(): SearchDao

    companion object {
        @Volatile
        private var instance: AppDataBase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDataBase(context).also { instance = it }
        }

        private fun createDataBase(context: Context) = Room.databaseBuilder(context.applicationContext,
            AppDataBase::class.java, "AppDB.db").build()
    }
}