package id.allana.githubapp_bfaa.ui.favorite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import id.allana.githubapp_bfaa.data.base.BaseViewModelImpl
import id.allana.githubapp_bfaa.data.base.Resource
import id.allana.githubapp_bfaa.data.model.local.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListUserFavoriteViewModel(private val listUserFavoriteRepository: ListUserFavoriteRepository): BaseViewModelImpl(), ListUserFavoriteContract.ViewModel {

    private val allUser = MutableLiveData<Resource<List<User>>>()
    override fun getAllUser() {
        allUser.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = listUserFavoriteRepository.getAllUser()
                viewModelScope.launch(Dispatchers.Main) {
                    allUser.value = Resource.Success(response)
                }
            } catch (e: Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                    allUser.value = Resource.Error(e.message.toString())
                }
            }
        }
    }


    override fun getAllUserLiveData(): MutableLiveData<Resource<List<User>>> = allUser

}