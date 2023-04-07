package id.allana.githubapp_bfaa.ui.detail

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import id.allana.githubapp_bfaa.R
import id.allana.githubapp_bfaa.data.base.BaseFragment
import id.allana.githubapp_bfaa.data.base.GenericViewModelFactory
import id.allana.githubapp_bfaa.data.base.Resource
import id.allana.githubapp_bfaa.data.datasource.local.UserDataSourceImpl
import id.allana.githubapp_bfaa.data.datasource.network.GithubUserDataSourceImpl
import id.allana.githubapp_bfaa.data.local.UserDatabase
import id.allana.githubapp_bfaa.data.model.local.User
import id.allana.githubapp_bfaa.data.model.network.ResponseDetailUser
import id.allana.githubapp_bfaa.databinding.FragmentDetailUserBinding
import id.allana.githubapp_bfaa.ui.detail.viewpager.SectionsPagerAdapter

class DetailUserFragment : BaseFragment<FragmentDetailUserBinding, DetailUserViewModel>(
    FragmentDetailUserBinding::inflate
), DetailUserContract.View {

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_following_user,
            R.string.tab_follower_user
        )
        const val EXTRA_USERNAME = "extra_username"
    }

    private var isFavorite = false
    private lateinit var username: String

    override fun initView() {
        val dataUsername by navArgs<DetailUserFragmentArgs>()
        dataUsername.username?.let {
            username = it
        }
        getDataDetail(username)
        getStatusFavorite(username)

        val mBundle = Bundle()
        mBundle.putString(EXTRA_USERNAME, dataUsername.username)

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.dataBundle = mBundle

        val viewPager: ViewPager2 = getViewBinding().viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = getViewBinding().tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    override fun initViewModel(): DetailUserViewModel {
        val networkDataSource = GithubUserDataSourceImpl()
        val localDataSource =
            UserDataSourceImpl(UserDatabase.getInstance(requireContext()).userDao())
        val repository = DetailUserRepository(networkDataSource, localDataSource)
        return GenericViewModelFactory(DetailUserViewModel(repository)).create(DetailUserViewModel::class.java)
    }

    override fun getDataDetail(path: String) {
        getViewModel().getDetailUser(path)
    }

    override fun getStatusFavorite(path: String) {
        getViewModel().getFavoriteUserByUsername(path)
    }

    override fun observeData() {
        super.observeData()
        getViewModel().getDetailUserLiveData().observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    showLoading(true)
                    showContent(false)
                    showError(false, null)
                }
                is Resource.Success -> {
                    showLoading(false)
                    showError(false, null)
                    showContent(true)
                    resource.data?.let { responseDetailUser ->
                        setDataDetail(responseDetailUser)
                    }
                }
                is Resource.Error -> {
                    showLoading(false)
                    showContent(false)
                    showError(true, getString(R.string.text_error_failed_get_data))
                }
            }
        }
        getViewModel().insertUserLiveData().observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {}
                is Resource.Success -> {
                    showError(true, "Add to favorite")
                }
                is Resource.Error -> {
                    showError(true, "Failed add to favorite")
                }
            }
        }
        getViewModel().deleteUserLiveData().observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {}
                is Resource.Success -> {
                    showError(true, "Delete from favorite")
                }
                is Resource.Error -> {
                    showError(true, "Failed delete from favorite")
                }
            }
        }

        /**
         * check if any data in favorite
         * use username to filter data
         * set value isFavorite true or false
         */
        getViewModel().getFavoriteUserByUsernameLiveData().observe(viewLifecycleOwner) { isFavorite ->
            this.isFavorite = isFavorite
            checkDataFavorite(isFavorite)
        }
    }

    override fun setDataDetail(data: ResponseDetailUser) {
        data.apply {
            Glide.with(requireContext())
                .load(avatarUrl)
                .into(getViewBinding().imgAvatar)
            getViewBinding().tvName.text = name

            if (bio.toString().isEmpty()) getViewBinding().tvBio.text =
                getString(R.string.text_bio_empty) else getViewBinding().tvBio.text = bio.toString()

            if (company.toString().isEmpty()) getViewBinding().tvCompany.text =
                getString(R.string.text_company_empty) else getViewBinding().tvCompany.text =
                company

            if (location.toString().isEmpty()) getViewBinding().tvLocation.text =
                getString(R.string.text_location_empty) else getViewBinding().tvLocation.text =
                location

            /**
             * handle user when click fab
             * insert data to favorite or delete data from favorite
             * change icon fab when clicked
             */
            getViewBinding().fabFavorite.setOnClickListener {
                if (isFavorite) {
                    getViewModel().deleteUser(this@DetailUserFragment.username)
                    isFavorite = !isFavorite
                    checkDataFavorite(isFavorite)
                } else {
                    getViewModel().insertUser(User(username = this.username, avatarUrl = this.avatarUrl, profileUrl = this.urlProfile))
                    isFavorite = !isFavorite
                    checkDataFavorite(isFavorite)
                }
            }
        }
    }

    /**
     * function to change icon fab when status isFavorite true or false
     */
    private fun checkDataFavorite(isFavorite: Boolean) {
        if (isFavorite) {
            getViewBinding().fabFavorite.setImageResource(R.drawable.baseline_favorite)
        } else {
            getViewBinding().fabFavorite.setImageResource(R.drawable.baseline_favorite_border)
        }
    }

    override fun showContent(isVisible: Boolean) {
        super.showContent(isVisible)
        getViewBinding().groupContent.isVisible = isVisible
    }

    override fun showError(isError: Boolean, msg: String?) {
        super.showError(isError, msg)
        if (isError) Snackbar.make(
            requireActivity().findViewById(android.R.id.content),
            msg.toString(),
            Snackbar.LENGTH_SHORT
        ).show()
    }

    override fun showLoading(isLoading: Boolean) {
        super.showLoading(isLoading)
        if (isLoading) {
            getViewBinding().progressBar.visibility = View.VISIBLE
        } else {
            getViewBinding().progressBar.visibility = View.INVISIBLE
        }
    }

}