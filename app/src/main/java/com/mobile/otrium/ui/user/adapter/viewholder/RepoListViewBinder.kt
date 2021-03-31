package com.mobile.otrium.ui.user.adapter.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobile.otrium.R
import com.mobile.otrium.ui.user.adapter.FeedAdapter
import com.mobile.otrium.ui.user.adapter.FeedItemBinder
import com.mobile.otrium.ui.user.adapter.FeedItemClass
import com.mobile.otrium.ui.user.adapter.FeedItemViewBinder
import com.mobile.otrium.models.UserRepoList
import com.mobile.otrium.models.UserRepo
import kotlinx.android.synthetic.main.adapter_repo_list.view.*

class RepoListViewBinder(val block: (data: UserRepo) -> Unit) :
    FeedItemViewBinder<UserRepoList, RepoListViewHolder>(
        UserRepoList::class.java
    ) {

    override fun createViewHolder(parent: ViewGroup): RepoListViewHolder {
        return RepoListViewHolder(
            LayoutInflater.from(parent.context).inflate(getFeedItemType(), parent, false), block
        )
    }

    override fun bindViewHolder(user: UserRepoList, viewHolder: RepoListViewHolder) {
        viewHolder.bind(user)
    }

    override fun getFeedItemType() = R.layout.adapter_repo_list

    override fun areContentsTheSame(oldItem: UserRepoList, newItem: UserRepoList) =
        oldItem == newItem

    override fun areItemsTheSame(oldItem: UserRepoList, newItem: UserRepoList): Boolean {
        return oldItem.id == newItem.id
    }
}


class RepoListViewHolder(val view: View, val block: (data: UserRepo) -> Unit) :
    RecyclerView.ViewHolder(view) {

    fun bind(data: UserRepoList) {

        var adapter: FeedAdapter? = null

        itemView.setOnClickListener {

        }

        itemView.apply {
            if (adapter == null) {
                val repoViewBinder = RepoViewBinder { userRepo: UserRepo ->
                    block(userRepo)
                }
                val viewBinders = mutableMapOf<FeedItemClass, FeedItemBinder>()

                viewBinders.put(
                    repoViewBinder.modelClass,
                    repoViewBinder as FeedItemBinder
                )
                adapter = FeedAdapter(viewBinders)
            }

            tv_horizontal_header?.text = data.title
            adapter_recycllerview?.apply {

                layoutManager = LinearLayoutManager(
                    adapter_recycllerview?.context,
                    LinearLayoutManager.HORIZONTAL, false
                )
                if (adapter_recycllerview?.adapter == null) {
                    adapter_recycllerview?.adapter = adapter
                }
                (adapter_recycllerview?.adapter as FeedAdapter).submitList(
                    data.userRepos as List<Any>? ?: emptyList()
                )
            }
        }
    }
}