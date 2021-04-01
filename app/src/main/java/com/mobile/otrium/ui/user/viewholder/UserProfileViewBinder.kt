package com.mobile.otrium.ui.user.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mobile.otrium.R
import com.mobile.otrium.ui.user.adapter.FeedViewBinder
import com.mobile.otrium.models.User
import kotlinx.android.synthetic.main.adapter_user_profile.view.*

/*
*  Profile binder
* */
class UserProfileViewBinder(val block: (data: User) -> Unit) :
    FeedViewBinder<User, UserProfileViewHolder>(
        User::class.java
    ) {

    override fun createViewHolder(parent: ViewGroup): UserProfileViewHolder {
        return UserProfileViewHolder(
            LayoutInflater.from(parent.context).inflate(getFeedType(), parent, false), block
        )
    }

    override fun bindViewHolder(model: User, viewHolder: UserProfileViewHolder) {
        viewHolder.bind(model)
    }

    override fun getFeedType() = R.layout.adapter_user_profile

    override fun areContentsTheSame(oldItem: User, newItem: User) =
        oldItem == newItem

    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.email == newItem.email
    }
}

/*
* UI set the parameter profile view
* */
class UserProfileViewHolder(val view: View, val block: (data: User) -> Unit) :
    RecyclerView.ViewHolder(view) {

    fun bind(data: User) {

        itemView.setOnClickListener {
            block(data)
        }

        itemView.apply {
            Glide
                .with(itemView.context)
                .load(data.avatarUrl)
                .circleCrop()
                .into(avatar)

            name.text = data.name
            login.text = data.login
            email.text = data.email
            followers.text =
                String.format(context.getText(R.string.followers) as String, data.followers)
            following.text =
                String.format(context.getText(R.string.following) as String, data.following)
        }
    }
}