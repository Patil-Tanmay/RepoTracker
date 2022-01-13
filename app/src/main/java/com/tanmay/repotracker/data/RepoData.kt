package com.tanmay.repotracker.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "Repositories"
)
data class RepoData(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val description: String?,
    val html_url: String,
    val full_name: String,
    val name: String
)
