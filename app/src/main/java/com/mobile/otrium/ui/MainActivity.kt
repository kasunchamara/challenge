package com.mobile.otrium.ui

import android.os.Bundle
import com.mobile.otrium.R
import dagger.android.support.DaggerAppCompatActivity

/*
* Launcher main activity
* */
class MainActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
