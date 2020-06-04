package com.mosh.songfinder.domain

import com.mosh.songfinder.data.dao.entity.SearchEntity
import java.io.Serializable

data class Search(
    val term: String
) : Serializable {
    fun toSearchEntity() = SearchEntity(term)
}