package id.allana.githubapp_bfaa.ui.detail.viewpager.listfollower

import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import id.allana.githubapp_bfaa.data.base.BaseFragment
import id.allana.githubapp_bfaa.data.base.GenericViewModelFactory
import id.allana.githubapp_bfaa.data.base.Resource
import id.allana.githubapp_bfaa.data.datasource.GithubUserDataSourceImpl
import id.allana.githubapp_bfaa.data.model.ItemsItem
import id.allana.githubapp_bfaa.databinding.FragmentListFollowerUserBinding

class ListFollowerUserFragment : BaseFragment<FragmentListFollowerUserBinding, ListFollowerViewModel>(
    FragmentListFollowerUserBinding::inflate
), ListFollowerContract.View {

    private lateinit var adapter: ListFollowerAdapter

    override fun initView() {
        initList()
        val username = arguments?.getString("EXTRA_USERNAME")
        username?.let {
            getDataDetail(it)
        }
    }

    override fun initViewModel(): ListFollowerViewModel {
        val dataSource = GithubUserDataSourceImpl()
        val repository = ListFollowerRepository(dataSource)
        return GenericViewModelFactory(ListFollowerViewModel(repository)).create(ListFollowerViewModel::class.java)
    }

    override fun initList() {
        adapter = ListFollowerAdapter()
        getViewBinding().rvFollowerUser.apply {
            adapter = this@ListFollowerUserFragment.adapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun getDataDetail(path: String) {
        getViewModel().getListFollower(path)
    }

    override fun setDataAdapter(listItem: List<ItemsItem>) {
        this@ListFollowerUserFragment.adapter.setListItems(listItem)
    }

    override fun observeData() {
        getViewModel().getListFollowerLiveData().observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    showLoading(true)
                    showContent(false)
                    showError(false, null)
                }
                is Resource.Success -> {
                    showLoading(false)
                    showContent(true)
                    showError(false, null)
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
        getViewBinding().rvFollowerUser.isVisible = isVisible
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