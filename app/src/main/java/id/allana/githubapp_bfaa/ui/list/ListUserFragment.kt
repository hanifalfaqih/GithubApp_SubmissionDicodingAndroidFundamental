package id.allana.githubapp_bfaa.ui.list

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import id.allana.githubapp_bfaa.R
import id.allana.githubapp_bfaa.data.base.BaseFragment
import id.allana.githubapp_bfaa.data.base.GenericViewModelFactory
import id.allana.githubapp_bfaa.data.base.Resource
import id.allana.githubapp_bfaa.data.datasource.network.GithubUserDataSourceImpl
import id.allana.githubapp_bfaa.data.model.network.ItemsItem
import id.allana.githubapp_bfaa.databinding.FragmentListUserBinding

class ListUserFragment : BaseFragment<FragmentListUserBinding, ListUserViewModel>(
    FragmentListUserBinding::inflate
), ListUserContract.View {

    private lateinit var adapter: ListUserAdapter

    override fun initView() {
        setTextEmptyData(true)
        setupMenu()
        initList()
    }

    override fun initViewModel(): ListUserViewModel {
        val dataSource = GithubUserDataSourceImpl()
        val repository = ListUserRepository(dataSource)
        return GenericViewModelFactory(ListUserViewModel(repository)).create(ListUserViewModel::class.java)
    }

    override fun initList() {
        adapter = ListUserAdapter()
        getViewBinding().rvGithubUser.apply {
            adapter = this@ListUserFragment.adapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun setDataAdapter(listItem: List<ItemsItem>) {
        this@ListUserFragment.adapter.setListItems(listItem)
    }

    override fun setupMenu() {
        (requireActivity() as MenuHost).addMenuProvider(object: MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.home_menu, menu)

                val search = menu.findItem(R.id.search_menu)
                val searchView = search.actionView as SearchView
                searchView.isSubmitButtonEnabled = true
                searchView.setOnQueryTextListener(object: OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        if (query != null) {
                            getViewModel().getSearchUser(query)
                        }
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean { return true }
                })
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean { return true }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun observeData() {
        getViewModel().getSearchUserLiveData().observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    showLoading(true)
                    showContent(false)
                    showError(false, null)
                    setTextEmptyData(false)
                }
                is Resource.Success -> {
                    showLoading(false)
                    showContent(true)
                    showError(false, null)
                    resource.data?.let { setDataAdapter(it) }
                    setTextEmptyData(false)
                }
                is Resource.Error -> {
                    showLoading(false)
                    showContent(false)
                    showError(true, getString(R.string.text_error_failed_get_data))
                    setTextEmptyData(false)
                }
            }
        }
    }

    override fun setTextEmptyData(isVisible: Boolean) {
        if (isVisible) {
            getViewBinding().tvEmptyData.visibility = View.VISIBLE
        } else {
            getViewBinding().tvEmptyData.visibility = View.INVISIBLE
        }
    }

    override fun showContent(isVisible: Boolean) {
        super.showContent(isVisible)
        getViewBinding().rvGithubUser.isVisible = isVisible
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