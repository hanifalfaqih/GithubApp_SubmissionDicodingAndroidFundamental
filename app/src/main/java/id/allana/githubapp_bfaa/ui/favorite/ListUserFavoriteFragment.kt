package id.allana.githubapp_bfaa.ui.favorite

import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import id.allana.githubapp_bfaa.R
import id.allana.githubapp_bfaa.data.base.BaseFragment
import id.allana.githubapp_bfaa.data.base.GenericViewModelFactory
import id.allana.githubapp_bfaa.data.base.Resource
import id.allana.githubapp_bfaa.data.datasource.local.UserDataSourceImpl
import id.allana.githubapp_bfaa.data.local.UserDatabase
import id.allana.githubapp_bfaa.data.model.local.User
import id.allana.githubapp_bfaa.databinding.FragmentListUserFavoriteBinding

class ListUserFavoriteFragment : BaseFragment<FragmentListUserFavoriteBinding, ListUserFavoriteViewModel>(
    FragmentListUserFavoriteBinding::inflate
), ListUserFavoriteContract.View {

    private lateinit var favoriteAdapter: ListUserFavoriteAdapter
    override fun initView() {
        initList()
    }

    override fun onResume() {
        super.onResume()
        getViewModel().getAllUser()
    }

    override fun initViewModel(): ListUserFavoriteViewModel {
        val dataSource = UserDataSourceImpl(UserDatabase.getInstance(requireContext()).userDao())
        val repository = ListUserFavoriteRepository(dataSource)
        return GenericViewModelFactory(ListUserFavoriteViewModel(repository)).create(ListUserFavoriteViewModel::class.java)
    }

    override fun initList() {
        favoriteAdapter = ListUserFavoriteAdapter()
        getViewBinding().rvFavoriteUser.apply {
            adapter = this@ListUserFavoriteFragment.favoriteAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun observeData() {
        getViewModel().getAllUserLiveData().observe(viewLifecycleOwner) { resource ->
            when(resource) {
                is Resource.Loading -> {
                    showLoading(true)
                    showContent(false)
                    showError(false)
                }
                is Resource.Success -> {
                    showLoading(false)
                    showContent(true)
                    resource.data?.let { setDataAdapter(it) }
                    showError(false)
                }
                is Resource.Error -> {
                    showLoading(false)
                    showContent(false)
                    showError(true, getString(R.string.text_error_failed_get_data))
                }
            }
        }
    }

    override fun showContent(isVisible: Boolean) {
        getViewBinding().rvFavoriteUser.isVisible = isVisible
    }

    override fun showLoading(isLoading: Boolean) {
        getViewBinding().progressBar.isVisible = isLoading
    }

    override fun showError(isError: Boolean, msg: String?) {
        if (isError) Snackbar.make(requireView().rootView, msg.toString(), Snackbar.LENGTH_SHORT).show()
    }

    override fun setDataAdapter(listItem: List<User>) {
        if (listItem.isNotEmpty()) {
            this@ListUserFavoriteFragment.favoriteAdapter.setListItems(listItem)
            stateDataEmpty(false)
        } else {
            stateDataEmpty(true)
        }
    }

    override fun stateDataEmpty(isVisible: Boolean) {
        if (isVisible) {
            getViewBinding().tvEmptyData.visibility = View.VISIBLE
        } else {
            getViewBinding().tvEmptyData.visibility = View.INVISIBLE
        }
    }

}