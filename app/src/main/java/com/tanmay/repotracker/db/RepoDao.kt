package com.tanmay.repotracker.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.tanmay.repotracker.data.GitRepo
import com.tanmay.repotracker.data.RepoData

@Dao
interface RepoDao {

    @Insert
    suspend fun insertRepo(repo : RepoData)

    @Query("SELECT * FROM Repositories")
    suspend fun getAllRepos() : List<RepoData>

    @Query("SELECT * FROM Repositories WHERE id=:id")
    suspend fun getRepo(id :Int) : RepoData

    @Query("DELETE FROM Repositories WHERE id=:id")
    suspend fun deleteRepo(id :String)
}