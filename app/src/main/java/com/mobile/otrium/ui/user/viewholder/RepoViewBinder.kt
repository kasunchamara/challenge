package com.mobile.otrium.ui.user.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mobile.otrium.R
import com.mobile.otrium.models.UserRepo
import com.mobile.otrium.ui.user.adapter.FeedViewBinder
import com.mobile.otrium.util.Constants
import kotlinx.android.synthetic.main.adapter_repo.view.*


/*
*  Repository binder
* */
class RepoViewBinder(private val block: (data: UserRepo) -> Unit) :
    FeedViewBinder<UserRepo, RepoViewHolder>(
        UserRepo::class.java
    ) {

    override fun createViewHolder(parent: ViewGroup): RepoViewHolder {
        return RepoViewHolder(
            LayoutInflater.from(parent.context).inflate(getFeedType(), parent, false), block
        )
    }

    override fun bindViewHolder(model: UserRepo, viewHolder: RepoViewHolder) {
        viewHolder.bind(model)
    }

    override fun getFeedType() = R.layout.adapter_repo

    override fun areContentsTheSame(oldItem: UserRepo, newItem: UserRepo) = oldItem == newItem

    override fun areItemsTheSame(oldItem: UserRepo, newItem: UserRepo): Boolean {
        return oldItem.id == newItem.id
    }
}

/*
* UI set the parameter repository
* */
class RepoViewHolder(val view: View, val block: (data: UserRepo) -> Unit) :
    RecyclerView.ViewHolder(view) {

    fun bind(data: UserRepo) {

        itemView.setOnClickListener {
            block(data)
        }

        itemView.apply {
            //Change the layout width
            if(Constants.MATCH_PARENT == data.layoutWidth){
                val view: View = findViewById(R.id.repoView)
                val layoutParams: ViewGroup.LayoutParams = view.layoutParams
                layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
                view.layoutParams = layoutParams
            }

            Glide
                .with(itemView.context)
                .load(data.avatarUrl)
                .circleCrop()
                .into(imageViewProfileImg)
            login.text = data.login
            repoNameText.text = data.repoName
            repoDescText.text = data.repoDesc
            countOfStars.text = if (data.starCount != -1) data.starCount.toString() else "n/a"
            primaryLanguageText.text = data.primaryLanguage

        }
    }
}