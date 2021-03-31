package com.mobile.otrium.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.mobile.otrium.ProfileQuery
import com.mobile.otrium.R
import com.mobile.otrium.repo.ProfileRepo
import com.mobile.otrium.ui.user.adapter.FeedAdapter
import com.mobile.otrium.ui.user.adapter.FeedItemBinder
import com.mobile.otrium.ui.user.adapter.FeedItemClass
import com.mobile.otrium.models.UserRepoList
import com.mobile.otrium.models.UserRepo
import com.mobile.otrium.models.User
import com.mobile.otrium.ui.contract.ProfileContract
import com.mobile.otrium.ui.user.adapter.viewholder.RepoListViewBinder
import com.mobile.otrium.ui.user.adapter.viewholder.UserProfileViewBinder
import com.mobile.otrium.util.Constants
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_profile.*
import javax.inject.Inject

class ProfileFragment : DaggerFragment(), ProfileContract.View,
    SwipeRefreshLayout.OnRefreshListener {

    companion object {
        private const val TAG = "ProfileFragment"
    }

    private val arrayListRepos: ArrayList<Any> = ArrayList()
    private val arrayListPinnedUserRepos: ArrayList<UserRepo> = ArrayList()
    private val arrayListTopUserRepos: ArrayList<UserRepo> = ArrayList()
    private val arrayListStarredUserRepos: ArrayList<UserRepo> = ArrayList()
    private val profile: ArrayList<User> = ArrayList()

    private var adapter: FeedAdapter? = null
    private var swipeContainer: SwipeRefreshLayout? = null

    @Inject
    lateinit var presenter: ProfilePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subscribeObservers()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeContainer = view.findViewById(R.id.swipeContainer)
        swipeContainer?.setOnRefreshListener(this)
        presenter.getProfileData(false)
    }

    private fun subscribeObservers() {
        presenter.getUserLiveData().observe(this, Observer {
            if (it != null) {
                addData(
                    it,
                    arrayListRepos,
                    arrayListPinnedUserRepos,
                    arrayListTopUserRepos,
                    arrayListStarredUserRepos
                )
            } else {
                println("User login: Error")
            }
        })

        presenter.getRefreshedUserLiveData().observe(this, Observer {
            if (it != null) {
                addData(it, ArrayList(), ArrayList(), ArrayList(), ArrayList())
            } else {
                println("User login: Error")
            }
        })
    }

    private fun addData(
        ProfileQuery: ProfileQuery.Data,
        list: ArrayList<Any>,
        listPinnedUserRepos: ArrayList<UserRepo>,
        listTopUserRepos: ArrayList<UserRepo>,
        listStarredUserRepos: ArrayList<UserRepo>,
    ) {

        val userProfileModel = User(
            ProfileQuery.repositoryOwner?.asUser?.avatarUrl as String,
            ProfileQuery.repositoryOwner.asUser.name ?: "n/a",
            ProfileQuery.repositoryOwner.asUser.login,
            ProfileQuery.repositoryOwner.asUser.email,
            ProfileQuery.repositoryOwner.asUser.followers.totalCount,
            ProfileQuery.repositoryOwner.asUser.following.totalCount
        )
        profile.add(userProfileModel)
        list.add(profile[0])

        ProfileQuery.repositoryOwner.asUser.pinnedItems.nodes?.let {
            for (node in it) {
                val userRepo: UserRepo =
                    UserRepo(
                        node?.asRepository?.id,
                        node?.asRepository?.owner?.avatarUrl as? String ?: "n/a",
                        node?.asRepository?.owner?.login ?: "n/a",
                        node?.asRepository?.name ?: "n/a",
                        node?.asRepository?.description ?: "n/a",
                        node?.asRepository?.primaryLanguage as? String ?: "n/a",
                        node?.asRepository?.stargazerCount ?: -1,
                    )
                listPinnedUserRepos.add(userRepo)
            }

            val pinnedReposListUser: UserRepoList =
                UserRepoList(
                    listPinnedUserRepos,
                    Constants.HORIZONTAL_LIST
                )
            pinnedReposListUser.title = "Pinned"
            list.add(pinnedReposListUser)
        }

        ProfileQuery.repositoryOwner.asUser.topRepositories.nodes?.let {
            for (node in it) {
                val userRepo: UserRepo =
                    UserRepo(
                        node?.id,
                        node?.owner?.avatarUrl as? String ?: "n/a",
                        node?.owner?.login ?: "n/a",
                        node?.name ?: "n/a",
                        node?.description ?: "n/a",
                        node?.primaryLanguage as? String ?: "n/a",
                        node?.stargazerCount ?: -1,
                    )
                listTopUserRepos.add(userRepo)
            }

            val topReposListUser: UserRepoList =
                UserRepoList(
                    listTopUserRepos,
                    Constants.HORIZONTAL_LIST
                )
            topReposListUser.title = "Top repositories"
            list.add(topReposListUser)
        }

        ProfileQuery.repositoryOwner.asUser.starredRepositories.nodes?.let {
            for (node in it) {
                val userRepo: UserRepo =
                    UserRepo(
                        node?.id,
                        node?.owner?.avatarUrl as? String ?: "n/a",
                        node?.owner?.login ?: "n/a",
                        node?.name ?: "n/a",
                        node?.description ?: "n/a",
                        node?.primaryLanguage as? String ?: "n/a",
                        node?.stargazerCount ?: -1,
                    )
                listStarredUserRepos.add(userRepo)
            }

            val starredReposListUser: UserRepoList =
                UserRepoList(
                    listStarredUserRepos,
                    Constants.HORIZONTAL_LIST
                )
            starredReposListUser.title = "Starred repositories"
            list.add(starredReposListUser)
        }

        showFeedItems(vertical_recyclerview, list)
    }

    private fun repoItemClick(data: UserRepo) {

    }

    private fun userProfileClick(data: User) {

    }

    private fun showFeedItems(recyclerView: RecyclerView, list: ArrayList<Any>?) {

        if (adapter == null) {
            val viewBinders = mutableMapOf<FeedItemClass, FeedItemBinder>()

            val userProfileViewBinder = UserProfileViewBinder { data: User ->
                userProfileClick(data)
            }

            val repoListViewBinder = RepoListViewBinder { data: UserRepo ->
                repoItemClick(data)
            }

            viewBinders[userProfileViewBinder.modelClass] = userProfileViewBinder as FeedItemBinder

            viewBinders[repoListViewBinder.modelClass] = repoListViewBinder as FeedItemBinder

            adapter = FeedAdapter(viewBinders)
        }

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
        if (recyclerView.adapter == null) {
            recyclerView.adapter = adapter
        }
        (recyclerView.adapter as FeedAdapter).submitList(list ?: emptyList())
    }

    override fun onRefresh() {
        Toast.makeText(context, "Refreshed", Toast.LENGTH_SHORT).show()
        swipeContainer?.isRefreshing = false
        presenter.getProfileData(true)
    }
}