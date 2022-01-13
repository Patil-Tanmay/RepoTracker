package com.tanmay.repotracker.api

import com.tanmay.repotracker.data.BranchItem
import com.tanmay.repotracker.data.CommitItem
import com.tanmay.repotracker.data.IssuesItem
import com.tanmay.repotracker.data.RepoData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApi {

    companion object {
        const val BASE_URL = " https://api.github.com/"
        const val STATUS_OKAY = 200
        const val STATUS_NOT_FOUND = 404
    }


    @GET("/repos/{owner}/{repo}")
    suspend fun getRepoDetails(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Response<RepoData>

    @GET("/repos/{owner}/{repo}/branches")
    suspend fun getBranches(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
    ): List<BranchItem>

    @GET("/repos/{owner}/{repo}/issues")
    suspend fun getIssues(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Query("state") state: String = "open"
    ): List<IssuesItem>

    @GET("/repos/{owner}/{repo}/commits")
    suspend fun getCommits(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Query("sha") branchName: String
    ) : List<CommitItem>
}