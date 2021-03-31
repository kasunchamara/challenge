package com.mobile.otrium.ui.base

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import com.mobile.otrium.ui.contract.BaseContract

open class BasePresenter<V : BaseContract.View> : ViewModel(), BaseContract.Presenter<V> {

    private var view: V? = null

    protected fun getView(): V? {
        return view
    }

    protected fun isViewAttached(): Boolean {
        return view != null
    }

    @CallSuper
    protected fun onViewAttached(view: V) {
        this.view = view
    }

    @CallSuper
    protected fun onViewDetached() {
        this.view = null
    }

    override fun onAttach(view: V) {
        TODO("Not yet implemented")
    }

    override fun onViewCreated() {
        TODO("Not yet implemented")
    }

    override fun onDestroy() {
        TODO("Not yet implemented")
    }
}