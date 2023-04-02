package id.allana.githubapp_bfaa.ui.detail.viewpager.listfollowing

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import id.allana.githubapp_bfaa.data.base.BaseViewModelImpl
import id.allana.githubapp_bfaa.data.base.Resource
import id.allana.githubapp_bfaa.data.model.network.ItemsItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListFollowingViewModel(private val listFollowingRepository: ListFollowingRepository): BaseViewModelImpl(), ListFollowingContract.ViewModel {

    private val listFollowingUserLiveData = MutableLiveData<Resource<List<ItemsItem>?>>()

    override fun getListFollowing(path: String) {
        listFollowingUserLiveData.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = listFollowingRepository.getListFollowing(path)
                viewModelScope.launch(Dispatchers.Main) {
                    listFollowingUserLiveData.value = Resource.Success(response)
                }
            } catch (e: Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                    listFollowingUserLiveData.value = Resource.Error(e.message.orEmpty())
                }
            }
        }
    }

    override fun getListFollowingLiveData(): LiveData<Resource<List<ItemsItem>?>> = listFollowingUserLiveData
}