package com.mosh.songfinder.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mosh.songfinder.data.dao.entity.SongEntity

@Dao
interface SongDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(songEntity: SongEntity)

    @Delete
    suspend fun delete(songEntity: SongEntity)

    @Query("SELECT * FROM song_db WHERE id = :id")
    fun get(id: Int): LiveData<SongEntity>

    @Query("SELECT * FROM song_db")
    fun getAll(): LiveData<List<SongEntity>>

    @Query("SELECT * FROM song_db WHERE search_id = :id")
    fun getAllByCollectionId(id: Int): LiveData<List<SongEntity>>
}