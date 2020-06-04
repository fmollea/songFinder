package com.mosh.songfinder.data.dao.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mosh.songfinder.domain.Search

@Entity(tableName = "search_db")
data class SearchEntity(
    @ColumnInfo(name = "term")
    val term: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

    fun toSearch() = Search(term)
}