package com.mosh.songfinder.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mosh.songfinder.data.dao.entity.SearchEntity

@Dao
interface SearchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(songEntity: SearchEntity)

    @Query("SELECT * FROM search_db WHERE id = :id")
    fun get(id: Int): LiveData<SearchEntity>

    @Query("SELECT * FROM search_db")
    fun getAll(): LiveData<List<SearchEntity>>
}