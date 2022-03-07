package com.tanmay.repotracker.utils

sealed class Resource{
    object Success: Resource()
    object Loading : Resource()
    object Failure : Resource()
    data class Error(val t : Throwable?) : Resource()
}

//object States{
//    fun loading(): Resource = Resource.Loading
//
//    fun failure(): Resource = Resource.Failure
//
//    fun error(error: Throwable): Resource.Error =
//        Resource.Error(error)
//
//    fun <T> success(data: T): Resource.Success<T> = Resource.Success(data)
//}
