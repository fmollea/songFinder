package com.mosh.songfinder.domain

import org.junit.Assert
import org.junit.Before
import org.junit.Test

class SearchTest {

    private lateinit var data: Search

    @Before
    fun setUp() {
        data = Search(TERM)
    }

    @Test
    fun checkTerm() {
        Assert.assertEquals(TERM, data.term)
    }

    companion object {
        const val TERM = "term"
    }
}