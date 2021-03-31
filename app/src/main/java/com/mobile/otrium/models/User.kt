package com.mobile.otrium.models

/*
* Model User
* @param avatarUrl : profile image url
* @param name : profile username
* @param login : profile login name
* @param email : profile email
* @param followers : profile followers number of count
* @param following : profile following number of count
* */
data class User(
    val avatarUrl: String,
    val name: String,
    val login: String,
    val email: String,
    val followers: Int,
    val following: Int
)