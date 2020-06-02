package com.mosh.songfinder.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mosh.songfinder.domain.Song

@Dao
interface SongDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(song: Song)

    @Delete
    suspend fun delete(songEntity: Song)

    @Query("SELECT * FROM song")
    fun getAll(): LiveData<List<Song>>

    @Query("SELECT * FROM song")
    fun getAllByCollectionId(id: Int): LiveData<List<Song>>
}