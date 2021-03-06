package com.mobile.otrium.models

import com.mobile.otrium.util.Constants

/*
* User repository list
* @param userRepos : array of user repository model
* @param id : list orientation
* @param title : list header title name
* */
data class UserRepoList(
    val userRepos: ArrayList<UserRepo>,
    val listOrientation: Int = Constants.HORIZONTAL,
    var title: String = ""
)