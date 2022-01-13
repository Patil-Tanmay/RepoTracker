package com.tanmay.repotracker.utils

sealed class Resource(
    val throwable: Throwable? = null
) {
    class Success() : Resource()
    class Loading : Resource()
    class Failure : Resource()
    class Error(t : Throwable) : Resource(t)
}
