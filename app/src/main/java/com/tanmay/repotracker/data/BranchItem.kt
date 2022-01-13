package com.tanmay.repotracker.data

data class BranchItem(
    val commit: Commit,
    val name: String,
    val `protected`: Boolean
)