package com.tanmay.repotracker.data

data class Label(
    val color: String,
    val default: Boolean,
    val description: String,
    val id: Int,
    val name: String,
    val node_id: String,
    val url: String
)