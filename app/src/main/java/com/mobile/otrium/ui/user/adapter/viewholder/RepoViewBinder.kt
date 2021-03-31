package com.mobile.otrium.ui.user.adapter.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mobile.otrium.R
import com.mobile.otrium.ui.user.adapter.FeedItemViewBinder
import com.mobile.otrium.models.UserRepo
import kotlinx.android.synthetic.main.adapter_repo.view.*

class RepoViewBinder(val block: (data: UserRepo) -> Unit) :
    FeedItemViewBinder<UserRepo, RepoViewHolder>(
        UserRepo::class.java
    ) {

    override fun createViewHolder(parent: ViewGroup): RepoViewHolder {
        return RepoViewHolder(
            LayoutInflater.from(parent.context).inflate(getFeedItemType(), parent, false), block
        )
    }

    override fun bindViewHolder(user: UserRepo, viewHolder: RepoViewHolder) {
        viewHolder.bind(user)
    }

    override fun getFeedItemType() = R.layout.adapter_repo

    override fun areContentsTheSame(oldItem: UserRepo, newItem: UserRepo) = oldItem == newItem

    override fun areItemsTheSame(oldItem: UserRepo, newItem: UserRepo): Boolean {
        return oldItem.id == newItem.id
    }
}


class RepoViewHolder(val view: View, val block: (data: UserRepo) -> Unit) :
    RecyclerView.ViewHolder(view) {

    fun bind(data: UserRepo) {

        itemView.setOnClickListener {
            block(data)
        }

        itemView.apply {
            Glide
                .with(itemView.context)
                .load(data.avatarUrl)
                .circleCrop()
                .into(imageViewProfileImg)
            textViewLogin.text = data.login
            textViewRepoName.text = data.repoName
            textViewRepoDesc.text = data.repoDesc
            textViewStars.text = if (data.starCount != -1) data.starCount.toString() else "n/a"
            textViewPrimaryLanguage.text = data.primaryLanguage

        }
    }
}