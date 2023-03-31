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
import id.allana.githubapp_bfaa.data.datasource.GithubUserDataSourceImpl
import id.allana.githubapp_bfaa.data.model.ResponseDetailUser
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
    }

    override fun initView() {
        val dataUsername by navArgs<DetailUserFragmentArgs>()
        dataUsername.username?.let {
            getDataDetail(it)
        }

        val mBundle = Bundle()
        mBundle.putString("EXTRA_USERNAME", dataUsername.username)

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
        val dataSource = GithubUserDataSourceImpl()
        val repository = DetailUserRepository(dataSource)
        return GenericViewModelFactory(DetailUserViewModel(repository)).create(DetailUserViewModel::class.java)
    }

    override fun getDataDetail(path: String) {
        getViewModel().getDetailUser(path)
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
                    resource.data?.let {
                        setDataDetail(it)
                    }
                }
                is Resource.Error -> {
                    showLoading(false)
                    showContent(false)
                    showError(true, "Gagal mendapatkan data")
                }
            }
        }
    }

    override fun setDataDetail(data: ResponseDetailUser) {
        data.apply {
            Glide.with(requireContext())
                .load(avatarUrl)
                .into(getViewBinding().imgAvatar)
            getViewBinding().tvName.text = name

            if (bio.toString().isNullOrEmpty()) getViewBinding().tvBio.text = getString(R.string.text_bio_empty) else getViewBinding().tvBio.text = bio.toString()

            if (company.toString().isNullOrEmpty()) getViewBinding().tvCompany.text = getString(R.string.text_company_empty) else getViewBinding().tvCompany.text = company

            if (location.toString().isNullOrEmpty()) getViewBinding().tvLocation.text = getString(R.string.text_location_empty) else getViewBinding().tvLocation.text = location
        }
    }

    override fun showContent(isVisible: Boolean) {
        super.showContent(isVisible)
        getViewBinding().groupContent.isVisible = isVisible
    }

    override fun showError(isError: Boolean, msg: String?) {
        super.showError(isError, msg)
        if (isError) Snackbar.make(requireActivity().findViewById(android.R.id.content), msg.toString(), Snackbar.LENGTH_SHORT).show()
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