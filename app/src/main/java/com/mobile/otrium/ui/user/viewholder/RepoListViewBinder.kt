package com.mobile.otrium.ui.user.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobile.otrium.R
import com.mobile.otrium.ui.user.adapter.FeedAdapter
import com.mobile.otrium.ui.user.adapter.FeedBinder
import com.mobile.otrium.ui.user.adapter.FeedItemClass
import com.mobile.otrium.ui.user.adapter.FeedViewBinder
import com.mobile.otrium.models.UserRepoList
import com.mobile.otrium.models.UserRepo
import kotlinx.android.synthetic.main.adapter_repo_list.view.*

/*
*  Repository list binder
* */
class RepoListViewBinder(private val block: (data: UserRepo) -> Unit) :
    FeedViewBinder<UserRepoList, RepoListViewHolder>(
        UserRepoList::class.java
    ) {

    override fun createViewHolder(parent: ViewGroup): RepoListViewHolder {
        return RepoListViewHolder(
            LayoutInflater.from(parent.context).inflate(getFeedType(), parent, false), block
        )
    }

    override fun bindViewHolder(model: UserRepoList, viewHolder: RepoListViewHolder) {
        viewHolder.bind(model)
    }

    override fun getFeedType() = R.layout.adapter_repo_list

    override fun areContentsTheSame(oldItem: UserRepoList, newItem: UserRepoList) =
        oldItem == newItem

    override fun areItemsTheSame(oldItem: UserRepoList, newItem: UserRepoList): Boolean {
        return oldItem.listOrientation == newItem.listOrientation
    }
}

/*
* UI set the parameter repository list
* */
class RepoListViewHolder(val view: View, val block: (data: UserRepo) -> Unit) :
    RecyclerView.ViewHolder(view) {

    fun bind(data: UserRepoList) {

        var adapter: FeedAdapter? = null

        itemView.setOnClickListener {

        }

        itemView.apply {
            if (adapter == null) {
                val repoViewBinder =
                    RepoViewBinder { userRepo: UserRepo ->
                        block(userRepo)
                    }
                val viewBinders = mutableMapOf<FeedItemClass, FeedBinder>()

                viewBinders[repoViewBinder.modelClass] = repoViewBinder as FeedBinder
                adapter =
                    FeedAdapter(viewBinders)
            }

            header?.text = data.title
            adapter_recyclerview?.apply {

                layoutManager = LinearLayoutManager(
                    adapter_recyclerview?.context,
                    data.listOrientation, false
                )
                if (adapter_recyclerview?.adapter == null) {
                    adapter_recyclerview?.adapter = adapter
                }
                (adapter_recyclerview?.adapter as FeedAdapter).submitList(
                    data.userRepos as List<Any>? ?: emptyList()
                )
            }
        }
    }
}