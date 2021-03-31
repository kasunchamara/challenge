package com.mobile.otrium.models

data class UserRepo(
    val id: String?,
    val avatarUrl: String,
    val login: String,
    val repoName: String,
    val repoDesc: String,
    val primaryLanguage: String,
    val starCount: Int
)