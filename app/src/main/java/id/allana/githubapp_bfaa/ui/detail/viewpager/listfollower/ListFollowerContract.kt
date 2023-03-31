package id.allana.githubapp_bfaa.ui.detail.viewpager.listfollower

import androidx.lifecycle.LiveData
import id.allana.githubapp_bfaa.data.base.BaseContract
import id.allana.githubapp_bfaa.data.base.Resource
import id.allana.githubapp_bfaa.data.model.ItemsItem
import id.allana.githubapp_bfaa.data.model.ResponseFollowerUser

interface ListFollowerContract {

    interface View: BaseContract.BaseView {
        fun initList()
        fun getDataDetail(path: String)
        fun setDataAdapter(listItem: List<ItemsItem>)
    }

    interface ViewModel: BaseContract.BaseViewModel {
        fun getListFollower(path: String)
        fun getListFollowerLiveData(): LiveData<Resource<List<ItemsItem>?>>
    }

    interface Repository: BaseContract.BaseRepository {
        suspend fun getListFollower(path: String): List<ItemsItem>
    }
}