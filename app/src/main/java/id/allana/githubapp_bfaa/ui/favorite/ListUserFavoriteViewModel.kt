package id.allana.githubapp_bfaa.ui.favorite

import androidx.lifecycle.LiveData
import id.allana.githubapp_bfaa.data.base.BaseViewModelImpl
import id.allana.githubapp_bfaa.data.model.local.User

class ListUserFavoriteViewModel(private val listUserFavoriteRepository: ListUserFavoriteRepository): BaseViewModelImpl(), ListUserFavoriteContract.ViewModel {

    override fun getAllUserLiveData(): LiveData<List<User>> {
        return listUserFavoriteRepository.getAllUser()
    }

}