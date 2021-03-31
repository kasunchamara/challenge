package com.mobile.otrium.models

data class User(
    val avatarUrl: String,
    val name: String,
    val login: String,
    val email: String,
    val followers: Int,
    val following: Int
)