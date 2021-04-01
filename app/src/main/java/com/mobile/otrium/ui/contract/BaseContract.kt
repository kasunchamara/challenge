package com.mobile.otrium.ui.contract

/*
* Base contract using common contract View and Presenter
* */
interface BaseContract {

    interface View {
        fun showProgress() // loading start data show progress
        fun hideProgress() // loading end data hide progress
    }

    interface Presenter<V : View> {
    }
}