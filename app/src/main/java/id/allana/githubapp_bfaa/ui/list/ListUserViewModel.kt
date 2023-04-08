package id.allana.githubapp_bfaa.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import id.allana.githubapp_bfaa.data.base.BaseViewModelImpl
import id.allana.githubapp_bfaa.data.base.Resource
import id.allana.githubapp_bfaa.data.model.ItemsItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListUserViewModel(private val listUserRepository: ListUserRepository): BaseViewModelImpl(), ListUserContract.ViewModel {

    private val listSearchUserLiveData = MutableLiveData<Resource<List<ItemsItem>?>>()

    override fun getSearchUser(query: String) {
        listSearchUserLiveData.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = listUserRepository.getSearchUser(query)
                viewModelScope.launch(Dispatchers.Main) {
                    listSearchUserLiveData.value = Resource.Success(response.items)
                }
            } catch (e: Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                    listSearchUserLiveData.value = Resource.Error(e.message.orEmpty())
                }
            }
        }
    }

    override fun getSearchUserLiveData(): LiveData<Resource<List<ItemsItem>?>> = listSearchUserLiveData
}