package com.mosh.songfinder.utils

import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import retrofit2.HttpException

class UtilsTest {

    @Test
    fun `check is network exception`() {
        val e = mock(HttpException::class.java)
        `when`(e.code()).thenReturn(404)

        Assert.assertTrue(Utils.isNetworkError(e))
    }

    @Test
    fun `check isnt network exception`() {
        val e = mock(HttpException::class.java)
        `when`(e.code()).thenReturn(500)

        Assert.assertFalse(Utils.isNetworkError(e))
    }

    @Test
    fun `check is a other exception`() {
        val e = mock(Throwable::class.java)

        Assert.assertFalse(Utils.isNetworkError(e))
    }
}