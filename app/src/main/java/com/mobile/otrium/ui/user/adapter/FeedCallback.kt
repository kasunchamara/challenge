package com.mobile.otrium.ui.user.adapter

import androidx.recyclerview.widget.DiffUtil

/*
* Feed callback using set the view binder items
* */
class FeedCallback(
    private val viewBinders: Map<FeedItemClass, FeedBinder>
) : DiffUtil.ItemCallback<Any>() {

    override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
        if (oldItem::class != newItem::class) {
            return false
        }
        return viewBinders[oldItem::class.java]?.areItemsTheSame(oldItem, newItem) ?: false
    }

    override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
        return viewBinders[oldItem::class.java]?.areContentsTheSame(oldItem, newItem) ?: false
    }
}