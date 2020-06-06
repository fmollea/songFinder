package com.mosh.songfinder.utils

import retrofit2.HttpException

object Utils {
    fun isNetworkError(e: Throwable) : Boolean {
        return if (e is HttpException) {
            e.code() in 400..499
        } else {
            false
        }
    }
}