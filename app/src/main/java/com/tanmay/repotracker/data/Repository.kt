package com.tanmay.repotracker.data

import com.tanmay.repotracker.api.GithubApi
import com.tanmay.repotracker.db.GitDb
import com.tanmay.repotracker.utils.Resource
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class Repository @Inject constructor(
    private val api: GithubApi,
    private val db: GitDb
) {
    val dao = db.repoDao()

    suspend fun getData(owner: String, repoName: String) = flow {
        try {
            val response = api.getRepoDetails(owner, repoName)
            if (response.code() == GithubApi.STATUS_OKAY) {
                val repoData = response.body()

                //check whether data exists already or not
                if (repoData != null) {
                    if (dao.getRepo(repoData.id) != null) {
                        emit(Resource.Failure)
                    } else {
                        dao.insertRepo(repoData)
                        emit(Resource.Success)
                    }
                }
            } else if (response.code() == GithubApi.STATUS_NOT_FOUND) {
                emit(Resource.Failure)
            } else {
                emit(Resource.Failure)
            }
        } catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }


}