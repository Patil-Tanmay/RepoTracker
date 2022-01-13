package com.tanmay.repotracker.api

import com.tanmay.repotracker.data.BranchItem
import com.tanmay.repotracker.data.GitRepo
import com.tanmay.repotracker.data.RepoData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApi {

    companion object{
        const val BASE_URL = " https://api.github.com/"
        const val STATUS_OKAY = 200
        const val STATUS_NOT_FOUND = 404
    }


    @GET("/repos/{owner}/{repo}")
    suspend fun getRepoDetails(
        @Path("owner") owner : String,
        @Path("repo") repo : String
    ) : Response<GitRepo>

    @GET("/repos/{owner}/{repo}/branches")
    suspend fun getBranches(
        @Path("owner") owner : String,
        @Path("repo") repo : String,
    ) : List<BranchItem>
}