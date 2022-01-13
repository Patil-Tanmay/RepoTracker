package com.tanmay.repotracker.ui

import androidx.lifecycle.ViewModel
import com.tanmay.repotracker.api.GithubApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.receiveAsFlow
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class BranchViewModel @Inject constructor(
    private val api : GithubApi
) : ViewModel(){

    private val _exception = Channel<Exception>()
    val exception = _exception.receiveAsFlow()

    fun getBranches(fullName : String) = flow{
        try {
            val splitFullName = fullName.split("/")
            val response = api.getBranches(splitFullName[0],splitFullName[1])
            if (!response.isNullOrEmpty()){
                emit(response)
            }else{
                emit(response)
            }
        }catch (e :Exception){
            _exception.send(e)
        }
    }

    fun getIssues(fullName: String) = flow {
        try {
            val splitFullName = fullName.split("/")
            val response = api.getIssues(splitFullName[0],splitFullName[1])
            if (!response.isNullOrEmpty()){
                emit(response)
            }else{
                emit(response)
            }
        }catch (e :Exception){
            _exception.send(e)
        }
    }

}