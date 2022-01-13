package com.tanmay.repotracker.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tanmay.repotracker.data.GitRepo
import com.tanmay.repotracker.data.RepoData

@Database(
    entities = [RepoData::class],
    version = 1
)
abstract class GitDb : RoomDatabase(){
    abstract fun repoDao() : RepoDao
}