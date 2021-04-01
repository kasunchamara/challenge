package com.mobile.otrium.ui.user.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

/*
*  Feed view binder class
* Recycler view create the view holder
* bind the view holder
* get the feed type
* */
abstract class FeedViewBinder<M, in VH : RecyclerView.ViewHolder>(
    val modelClass: Class<out M>
) : DiffUtil.ItemCallback<M>() {

    abstract fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder
    abstract fun bindViewHolder(model: M, viewHolder: VH)
    abstract fun getFeedType(): Int
}