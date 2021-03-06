package com.tanmay.repotracker.data

import com.fasterxml.jackson.annotation.JsonProperty


data class Reactions(
    @param:JsonProperty("+1")
    val PLUS : Int?=null,
    @param:JsonProperty("-1")
    val MINUS : Int?=null,
    val confused: Int,
    val eyes: Int,
    val heart: Int,
    val hooray: Int,
    val laugh: Int,
    val rocket: Int,
    val total_count: Int,
    val url: String
)