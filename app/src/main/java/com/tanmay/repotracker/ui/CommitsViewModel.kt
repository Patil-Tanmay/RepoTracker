package com.tanmay.repotracker.ui

import androidx.lifecycle.ViewModel
import com.tanmay.repotracker.api.GithubApi
import com.tanmay.repotracker.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.receiveAsFlow
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class CommitsViewModel @Inject constructor(
    private val api: GithubApi
) : ViewModel() {

    private val _progress = Channel<Resource>()
    val progress = _progress.receiveAsFlow()

    private val _error = Channel<Exception>()
    val errors = _error.receiveAsFlow()

    fun getALlCommits(branchName: String,fullName:String) = flow{
        try {
            _progress.send(Resource.Loading)
            val fullNameSplit = fullName.split("/")
            val response = api.getCommits(fullNameSplit[0],fullNameSplit[1],branchName)
            if (!response.isNullOrEmpty()){
                emit(response)
                _progress.send(Resource.Success(response))
            }else{
                _progress.send(Resource.Failure)
                emit(response)
            }
        }catch (e :Exception){
            _progress.send(Resource.Failure)
            _error.send(e)
        }
    }
}