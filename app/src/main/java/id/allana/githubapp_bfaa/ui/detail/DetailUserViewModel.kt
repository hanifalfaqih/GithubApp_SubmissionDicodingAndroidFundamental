package id.allana.githubapp_bfaa.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import id.allana.githubapp_bfaa.data.base.BaseViewModelImpl
import id.allana.githubapp_bfaa.data.base.Resource
import id.allana.githubapp_bfaa.data.model.ResponseDetailUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeoutException

class DetailUserViewModel(private val detailUserRepository: DetailUserRepository): BaseViewModelImpl(), DetailUserContract.ViewModel {

    private val detailUserLiveData = MutableLiveData<Resource<ResponseDetailUser>>()

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
}