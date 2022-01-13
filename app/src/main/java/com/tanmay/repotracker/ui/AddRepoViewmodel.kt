package com.tanmay.repotracker.ui

import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.tanmay.repotracker.api.GithubApi
import com.tanmay.repotracker.api.GithubApi.Companion.STATUS_NOT_FOUND
import com.tanmay.repotracker.api.GithubApi.Companion.STATUS_OKAY
import com.tanmay.repotracker.data.RepoData
import com.tanmay.repotracker.data.Repository
import com.tanmay.repotracker.db.GitDb
import com.tanmay.repotracker.db.RepoDao
import com.tanmay.repotracker.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class AddRepoViewmodel @Inject constructor(
    private val repository: Repository
) : ViewModel() {


    private var _isRepoExists = MutableStateFlow<Resource>(Resource.Loading)
    val isRepoExists = _isRepoExists

    private val _error = Channel<Exception>()
    val error = _error.receiveAsFlow()


    fun checkRepo(owner: String, repoName: String) {
        viewModelScope.launch {
            repository.getData(owner,repoName).collect {
                when(it){
                    is Resource.Success<*> -> {
                        _isRepoExists.value = it
                    }

                    is Resource.Failure -> {
                        _isRepoExists.value = it
                    }

                    is Resource.Error -> {
                        _isRepoExists.value = it
                        _error.send(it.t as Exception)
                    }

                    else -> {}
                }
            }
        }
    }



}