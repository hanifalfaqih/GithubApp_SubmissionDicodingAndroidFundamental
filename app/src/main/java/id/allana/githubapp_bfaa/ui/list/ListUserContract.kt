package id.allana.githubapp_bfaa.ui.list

import androidx.lifecycle.LiveData
import id.allana.githubapp_bfaa.data.base.BaseContract
import id.allana.githubapp_bfaa.data.base.Resource
import id.allana.githubapp_bfaa.data.model.network.ItemsItem
import id.allana.githubapp_bfaa.data.model.network.ResponseSearchUser

interface ListUserContract {

    interface View: BaseContract.BaseView {
        fun initList()
        fun setDataAdapter(listItem: List<ItemsItem>)
        fun setupMenu()
        fun setTextEmptyData(isVisible: Boolean)
    }

    interface ViewModel: BaseContract.BaseViewModel {
        fun getThemeSetting(): LiveData<Boolean>

        fun getSearchUser(query: String)
        fun getSearchUserLiveData(): LiveData<Resource<List<ItemsItem>?>>
    }

    interface Repository: BaseContract.BaseRepository {
        fun getThemeSetting(): LiveData<Boolean>

        suspend fun getSearchUser(query: String): ResponseSearchUser
    }

}