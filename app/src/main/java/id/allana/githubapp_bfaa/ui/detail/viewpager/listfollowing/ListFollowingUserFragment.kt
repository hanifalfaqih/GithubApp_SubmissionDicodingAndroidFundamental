package id.allana.githubapp_bfaa.ui.detail.viewpager.listfollowing

import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import id.allana.githubapp_bfaa.data.base.BaseFragment
import id.allana.githubapp_bfaa.data.base.GenericViewModelFactory
import id.allana.githubapp_bfaa.data.base.Resource
import id.allana.githubapp_bfaa.data.datasource.GithubUserDataSourceImpl
import id.allana.githubapp_bfaa.data.model.ItemsItem
import id.allana.githubapp_bfaa.databinding.FragmentListFollowingUserBinding


class ListFollowingUserFragment : BaseFragment<FragmentListFollowingUserBinding, ListFollowingViewModel>(
    FragmentListFollowingUserBinding::inflate
), ListFollowingContract.View {

    private lateinit var adapter: ListFollowingAdapter

    override fun initView() {
        initList()
        val username = arguments?.getString("EXTRA_USERNAME")
        username?.let {
            getDataDetail(it)
        }
    }

    override fun initViewModel(): ListFollowingViewModel {
        val dataSource = GithubUserDataSourceImpl()
        val repository = ListFollowingRepository(dataSource)
        return GenericViewModelFactory(ListFollowingViewModel(repository)).create(ListFollowingViewModel::class.java)
    }

    override fun initList() {
        adapter = ListFollowingAdapter()
        getViewBinding().rvFollowingUser.apply {
            adapter = this@ListFollowingUserFragment.adapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun getDataDetail(path: String) {
        getViewModel().getListFollowing(path)
    }

    override fun setDataAdapter(listItem: List<ItemsItem>) {
        this@ListFollowingUserFragment.adapter.setListItems(listItem)
    }

    override fun observeData() {
        super.observeData()
        getViewModel().getListFollowingLiveData().observe(viewLifecycleOwner) { resource ->
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
                        setDataAdapter(it)
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

    override fun showContent(isVisible: Boolean) {
        super.showContent(isVisible)
        getViewBinding().rvFollowingUser.isVisible = isVisible
    }

    override fun showLoading(isLoading: Boolean) {
        super.showLoading(isLoading)
        if (isLoading) {
            getViewBinding().progressBar.visibility = View.VISIBLE
        } else {
            getViewBinding().progressBar.visibility = View.INVISIBLE
        }
    }

    override fun showError(isError: Boolean, msg: String?) {
        super.showError(isError, msg)
        if (isError) Snackbar.make(requireActivity().findViewById(android.R.id.content), msg.toString(), Snackbar.LENGTH_SHORT).show()
    }
}