package com.mobile.otrium.models

import com.mobile.otrium.util.Constants

data class UserRepoList(
    val userRepos: ArrayList<UserRepo>,
    val id: Int = Constants.HORIZONTAL_LIST,
    var title: String = ""
)