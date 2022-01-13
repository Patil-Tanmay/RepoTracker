package com.tanmay.repotracker.data

data class CommitItem(
    val commit: Commit,
    val committer: CommitterX?,
    val sha: String
)