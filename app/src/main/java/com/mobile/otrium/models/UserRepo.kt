package com.mobile.otrium.models

/*
* Model User repository
* @param id : user repository id
* @param avatarUrl : user repository profile image url
* @param login : user username
* @param repoName : user repository name
* @param repoDesc : user repository Description
* @param primaryLanguage : user repository using primary language
* @param starCount : user repository number of stars count
* */
data class UserRepo(
    val id: String?,
    val avatarUrl: String,
    val login: String,
    val repoName: String,
    val repoDesc: String,
    val primaryLanguage: String,
    val starCount: Int
)