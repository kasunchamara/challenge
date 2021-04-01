package com.mobile.otrium.ui.user.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

typealias FeedItemClass = Class<out Any>
typealias FeedBinder = FeedViewBinder<Any, RecyclerView.ViewHolder>

/*
* Feed adapter
*
* */
class FeedAdapter(
    private val viewBinders: Map<FeedItemClass, FeedBinder>
) : ListAdapter<Any, RecyclerView.ViewHolder>(
    FeedCallback(
        viewBinders
    )
) {

    private val viewTypeToBinders = viewBinders.mapKeys { it.value.getFeedType() }

    private fun getViewBinder(viewType: Int): FeedBinder = viewTypeToBinders.getValue(viewType)

    override fun getItemViewType(position: Int): Int =
        viewBinders.getValue(super.getItem(position).javaClass).getFeedType()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return getViewBinder(viewType).createViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        return getViewBinder(getItemViewType(position)).bindViewHolder(getItem(position), holder)
    }

}