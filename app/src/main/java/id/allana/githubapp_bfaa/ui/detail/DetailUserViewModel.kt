package id.allana.githubapp_bfaa.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import id.allana.githubapp_bfaa.data.base.BaseViewModelImpl
import id.allana.githubapp_bfaa.data.base.Resource
import id.allana.githubapp_bfaa.data.model.local.User
import id.allana.githubapp_bfaa.data.model.network.ResponseDetailUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailUserViewModel(private val detailUserRepository: DetailUserRepository): BaseViewModelImpl(), DetailUserContract.ViewModel {

    private val detailUserLiveData = MutableLiveData<Resource<ResponseDetailUser>>()
    private val deleteUserLiveData = MutableLiveData<Resource<Int>>()
    private val insertUserLiveData = MutableLiveData<Resource<Long>>()
    private val favoriteUserLiveData = MutableLiveData<Boolean>()

    override fun getDetailUser(path: String) {
        detailUserLiveData.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = detailUserRepository.getDetailUser(path)
                viewModelScope.launch(Dispatchers.Main) {
                    detailUserLiveData.value = Resource.Success(response)
                }
            } catch (e: Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                    detailUserLiveData.value = Resource.Error(e.message.orEmpty())
                }
            }
        }
    }

    override fun getDetailUserLiveData(): LiveData<Resource<ResponseDetailUser>> = detailUserLiveData
    override fun getFavoriteUserByUsername(username: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = detailUserRepository.getFavoriteUserByUsername(username)
            viewModelScope.launch(Dispatchers.Main) {
                favoriteUserLiveData.value = response
            }
        }
    }

    override fun getFavoriteUserByUsernameLiveData(): LiveData<Boolean> = favoriteUserLiveData

    override fun deleteUser(username: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = detailUserRepository.deleteUser(username)
                viewModelScope.launch(Dispatchers.Main) {
                    deleteUserLiveData.value = Resource.Success(response)
                }
            } catch (e: Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                    deleteUserLiveData.value = Resource.Error(e.message.orEmpty())
                }
            }
        }
    }

    override fun deleteUserLiveData(): LiveData<Resource<Int>> = deleteUserLiveData

    override fun insertUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = detailUserRepository.insertUser(user)
                viewModelScope.launch(Dispatchers.Main) {
                    insertUserLiveData.value = Resource.Success(response)
                }
            } catch (e: Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                    insertUserLiveData.value = Resource.Error(e.message.orEmpty())
                }
            }
        }
    }

    override fun insertUserLiveData(): LiveData<Resource<Long>> = insertUserLiveData
}