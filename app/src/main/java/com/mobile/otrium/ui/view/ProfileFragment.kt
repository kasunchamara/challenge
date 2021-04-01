package com.mobile.otrium.ui.view

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.mobile.otrium.ProfileQuery
import com.mobile.otrium.R
import com.mobile.otrium.models.User
import com.mobile.otrium.models.UserRepo
import com.mobile.otrium.models.UserRepoList
import com.mobile.otrium.ui.contract.ProfileContract
import com.mobile.otrium.ui.presenter.ProfilePresenter
import com.mobile.otrium.ui.user.adapter.FeedAdapter
import com.mobile.otrium.ui.user.adapter.FeedBinder
import com.mobile.otrium.ui.user.adapter.FeedItemClass
import com.mobile.otrium.ui.user.viewholder.RepoListViewBinder
import com.mobile.otrium.ui.user.viewholder.UserProfileViewBinder
import com.mobile.otrium.util.Constants
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_profile.*
import javax.inject.Inject

/*
* View profile fragment use MainActivity layout
* */
class ProfileFragment : DaggerFragment(), ProfileContract.View,
    SwipeRefreshLayout.OnRefreshListener {

    private val arrayListRepos: ArrayList<Any> = ArrayList() // repository main list
    private val arrayListPinnedUserRepos: ArrayList<UserRepo> = ArrayList() // pinned repository list
    private val arrayListTopUserRepos: ArrayList<UserRepo> = ArrayList() // top repository list
    private val arrayListStarredUserRepos: ArrayList<UserRepo> = ArrayList() // starred repository list
    private val profile: ArrayList<User> = ArrayList() // profile detail list

    private var adapter: FeedAdapter? = null
    private var swipeContainer: SwipeRefreshLayout? = null

    @Inject
    lateinit var presenter: ProfilePresenter // inject profile presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subscribeObservers() //get the data
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
        presenter.getProfileData(false) // presenter call the get data main presenter method
    }

    //get user data
    override fun getUserLiveData(data: MutableLiveData<ProfileQuery.Data>) {
        data.observe(this, Observer {
            if (it != null) {
                addData(
                    it,
                    arrayListRepos,
                    arrayListPinnedUserRepos,
                    arrayListTopUserRepos,
                    arrayListStarredUserRepos
                )
            } else {
                showErrorDialog()
            }
        })
    }

    //get user refresh data
    override fun getRefreshUserLiveData(data: MutableLiveData<ProfileQuery.Data>) {
        data.observe(this, Observer {
            if (it != null) {
                addData(it, ArrayList(), ArrayList(), ArrayList(), ArrayList())
            } else {
                showErrorDialog()
            }
        })
    }

    // connection or auth error popup this dialog
    private fun showErrorDialog() {
        val builder = context?.let { AlertDialog.Builder(it) }
        builder?.setTitle(R.string.error_title)
        builder?.setMessage(R.string.error_desc)
        builder?.setPositiveButton(R.string.ok){ _: DialogInterface?, _: Int ->
            (activity as MainActivity).onBack()
        }
        val alertDialog: AlertDialog = builder!!.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    // loading progress bar show method
    override fun showProgress() {
        if (progressBar?.visibility != View.VISIBLE) {
            progressBar?.visibility = View.VISIBLE
        }
    }

    // loading progress bar hide method
    override fun hideProgress() {
        if (progressBar?.visibility == View.VISIBLE) {
            progressBar?.visibility = View.INVISIBLE
        }
    }

    private fun subscribeObservers() {
        presenter.getUserLiveData()
        presenter.getRefreshedUserLiveData()
    }

    // add graphql response data
    private fun addData(
        ProfileQuery: ProfileQuery.Data,
        list: ArrayList<Any>,
        listPinnedUserRepos: ArrayList<UserRepo>,
        listTopUserRepos: ArrayList<UserRepo>,
        listStarredUserRepos: ArrayList<UserRepo>,
    ) {

        //profile model
        val userProfileModel = User(
            ProfileQuery.repositoryOwner?.asUser?.avatarUrl as? String ?: "n/a",
            ProfileQuery.repositoryOwner?.asUser?.name ?: "n/a",
            ProfileQuery.repositoryOwner?.asUser?.login ?: "n/a",
            ProfileQuery.repositoryOwner?.asUser?.email ?: "n/a",
            ProfileQuery.repositoryOwner?.asUser?.followers?.totalCount ?: 0,
            ProfileQuery.repositoryOwner?.asUser?.following?.totalCount ?: 0
        )
        profile.add(userProfileModel)
        list.add(profile[0])

        //pinned item
        ProfileQuery.repositoryOwner?.asUser?.pinnedItems?.nodes?.let {
            for (node in it) {
                val userRepo =
                    UserRepo(
                        node?.asRepository?.id,
                        node?.asRepository?.owner?.avatarUrl as? String ?: "n/a",
                        node?.asRepository?.owner?.login ?: "n/a",
                        node?.asRepository?.name ?: "n/a",
                        node?.asRepository?.description ?: "n/a",
                        node?.asRepository?.primaryLanguage?.name ?: "n/a",
                        node?.asRepository?.stargazerCount ?: -1,
                        Constants.MATCH_PARENT
                    )
                listPinnedUserRepos.add(userRepo)
            }

            val pinnedReposListUser =
                UserRepoList(
                    listPinnedUserRepos,
                    Constants.VERTICAL
                )
            pinnedReposListUser.title = context?.resources?.getString(R.string.pinned).toString()
            list.add(pinnedReposListUser)
        }

        //top repository item
        ProfileQuery.repositoryOwner?.asUser?.topRepositories?.nodes?.let {
            for (node in it) {
                val userRepo =
                    UserRepo(
                        node?.id,
                        node?.owner?.avatarUrl as? String ?: "n/a",
                        node?.owner?.login ?: "n/a",
                        node?.name ?: "n/a",
                        node?.description ?: "n/a",
                        node?.primaryLanguage?.name  ?: "n/a",
                        node?.stargazerCount ?: -1,
                        Constants.WRAP_CONTENT
                    )
                listTopUserRepos.add(userRepo)
            }

            val topReposListUser =
                UserRepoList(
                    listTopUserRepos,
                    Constants.HORIZONTAL
                )
            topReposListUser.title = context?.resources?.getString(R.string.top_repo).toString()
            list.add(topReposListUser)
        }

        // starred repository items
        ProfileQuery.repositoryOwner?.asUser?.starredRepositories?.nodes?.let {
            for (node in it) {
                val userRepo =
                    UserRepo(
                        node?.id,
                        node?.owner?.avatarUrl as? String ?: "n/a",
                        node?.owner?.login ?: "n/a",
                        node?.name ?: "n/a",
                        node?.description ?: "n/a",
                        node?.primaryLanguage?.name  ?: "n/a",
                        node?.stargazerCount ?: -1,
                        Constants.WRAP_CONTENT
                    )
                listStarredUserRepos.add(userRepo)
            }

            val starredReposListUser =
                UserRepoList(
                    listStarredUserRepos,
                    Constants.HORIZONTAL
                )
            starredReposListUser.title = context?.resources?.getString(R.string.starred_repo).toString()
            list.add(starredReposListUser)
        }

        showFeedItems(vertical_recyclerview, list)
    }

    private fun repoItemClick() {

    }

    private fun userProfileClick() {

    }

    // load data listview
    private fun showFeedItems(recyclerView: RecyclerView, list: ArrayList<Any>?) {

        if (adapter == null) {
            val viewBinders = mutableMapOf<FeedItemClass, FeedBinder>()

            val userProfileViewBinder =
                UserProfileViewBinder {
                    userProfileClick()
                }

            val repoListViewBinder =
                RepoListViewBinder {
                    repoItemClick()
                }

            viewBinders[userProfileViewBinder.modelClass] = userProfileViewBinder as FeedBinder

            viewBinders[repoListViewBinder.modelClass] = repoListViewBinder as FeedBinder

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

    //pull and refresh data
    override fun onRefresh() {
        swipeContainer?.isRefreshing = false
        presenter.getProfileData(true)
    }

}