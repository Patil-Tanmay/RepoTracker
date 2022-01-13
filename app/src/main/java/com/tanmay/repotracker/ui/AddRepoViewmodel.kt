package com.tanmay.repotracker.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tanmay.repotracker.api.GithubApi
import com.tanmay.repotracker.api.GithubApi.Companion.STATUS_NOT_FOUND
import com.tanmay.repotracker.api.GithubApi.Companion.STATUS_OKAY
import com.tanmay.repotracker.data.RepoData
import com.tanmay.repotracker.db.GitDb
import com.tanmay.repotracker.db.RepoDao
import com.tanmay.repotracker.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class AddRepoViewmodel @Inject constructor(
    private val api: GithubApi,
    db: GitDb
) : ViewModel() {

    private var dao: RepoDao = db.repoDao()

    private var _isRepoExists = MutableStateFlow<Resource>(Resource.Loading())
    val isRepoExists = _isRepoExists

    fun checkRepo(owner: String, repoName: String) {
        viewModelScope.launch {
            getData(owner,repoName)
        }
    }

    private suspend fun getData(owner: String, repoName: String) {

            try {
                val response = api.getRepoDetails(owner, repoName)
                if (response.code() == STATUS_OKAY) {
                    val repoData = response.body()?.let { gitRepo ->
                        RepoData(
                            id = gitRepo.id,
                            description = gitRepo.description,
                            html_url = gitRepo.html_url,
                            full_name = gitRepo.full_name,
                            name = gitRepo.name
                        )
                    }
                    if (repoData != null) {
                        insertDataInDB(repoData)
                    }
                } else if (response.code() == STATUS_NOT_FOUND) {
                    _isRepoExists.value = Resource.Failure()
                } else {
                    _isRepoExists.value = Resource.Failure()
                }
            } catch (e: Exception) {
                _isRepoExists.value = Resource.Error(e)
            }

    }

    private suspend fun insertDataInDB(repoData :RepoData){
        if (dao.getRepo(repoData.id) !=null){
            _isRepoExists.value = Resource.Failure()
        }else{
            dao.insertRepo(repoData)
            _isRepoExists.value = Resource.Success()
        }
    }


}