package id.allana.githubapp_bfaa.ui.favorite

import androidx.lifecycle.LiveData
import id.allana.githubapp_bfaa.data.base.BaseContract
import id.allana.githubapp_bfaa.data.model.local.User

interface ListUserFavoriteContract {

    interface View: BaseContract.BaseView {
        fun initList()
        fun setDataAdapter(listItem: List<User>)
        fun setupMenu()
        fun setTextEmptyData(isVisible: Boolean)
    }

    interface ViewModel: BaseContract.BaseViewModel {
        fun getAllUserLiveData(): LiveData<List<User>>
    }

    interface Repository: BaseContract.BaseRepository {
        fun getAllUser(): LiveData<List<User>>
    }

}