package id.allana.githubapp_bfaa.ui.detail.viewpager.listfollower

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import id.allana.githubapp_bfaa.data.base.BaseViewModelImpl
import id.allana.githubapp_bfaa.data.base.Resource
import id.allana.githubapp_bfaa.data.model.network.ItemsItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListFollowerViewModel(private val listFollowerRepository: ListFollowerRepository): BaseViewModelImpl(), ListFollowerContract.ViewModel {

    private val listFollowerUserLiveData = MutableLiveData<Resource<List<ItemsItem>?>>()

    override fun getListFollower(path: String) {
        listFollowerUserLiveData.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO)  {
            try {
                val response = listFollowerRepository.getListFollower(path)
                viewModelScope.launch(Dispatchers.Main) {
                    listFollowerUserLiveData.value = Resource.Success(response)
                }
            } catch (e: Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                    listFollowerUserLiveData.value = Resource.Error(e.message.toString())
                }
            }
        }
    }

    override fun getListFollowerLiveData(): LiveData<Resource<List<ItemsItem>?>> = listFollowerUserLiveData
}