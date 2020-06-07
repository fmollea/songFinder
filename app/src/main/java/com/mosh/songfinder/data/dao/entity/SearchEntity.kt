package com.mosh.songfinder.data.dao.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.mosh.songfinder.domain.Search

@Entity(tableName = "search_db")
data class SearchEntity(
    @PrimaryKey
    @ColumnInfo(name = "term")
    val term: String
) {
    @Ignore
    fun toSearch() = Search(term)
}