package com.tanmay.repotracker.ui

import androidx.lifecycle.ViewModel
import com.tanmay.repotracker.api.GithubApi
import com.tanmay.repotracker.db.GitDb
import com.tanmay.repotracker.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class ReposViewModel @Inject constructor(
    private val api : GithubApi,
    private val db: GitDb
) : ViewModel(){

    /**
     * Here Loading Is Initial Stage,we can show ProgressBar at that time.
     * Failure Represents empty data Stage
     * Success -> RepoData is not empty stage
     */

    private val _repoDataStatus = MutableStateFlow<Resource>(Resource.Loading)
    val repoDataStatus : StateFlow<Resource> = _repoDataStatus


    fun getReposData() = flow{
        val repos = db.repoDao().getAllRepos()
        if (repos.isNullOrEmpty()){
            _repoDataStatus.value = Resource.Failure
            emit(repos)
        }else{
            _repoDataStatus.value = Resource.Success(repos)
            emit(repos)
        }
    }
}