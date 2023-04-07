package id.allana.githubapp_bfaa.ui.favorite

import androidx.lifecycle.MutableLiveData
import id.allana.githubapp_bfaa.data.base.BaseContract
import id.allana.githubapp_bfaa.data.base.Resource
import id.allana.githubapp_bfaa.data.model.local.User

interface ListUserFavoriteContract {

    interface View: BaseContract.BaseView {
        fun initList()
        fun setDataAdapter(listItem: List<User>)
        fun stateDataEmpty(isVisible: Boolean)
    }

    interface ViewModel: BaseContract.BaseViewModel {
        fun getAllUser()
        fun getAllUserLiveData(): MutableLiveData<Resource<List<User>>>
    }

    interface Repository: BaseContract.BaseRepository {
        suspend fun getAllUser(): List<User>
    }

}