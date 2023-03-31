package id.allana.githubapp_bfaa.ui.detail.viewpager.listfollowing

import androidx.lifecycle.LiveData
import id.allana.githubapp_bfaa.data.base.BaseContract
import id.allana.githubapp_bfaa.data.base.Resource
import id.allana.githubapp_bfaa.data.model.ItemsItem
import id.allana.githubapp_bfaa.data.model.ResponseFollowingUser

interface ListFollowingContract {

    interface View: BaseContract.BaseView {
        fun initList()
        fun getDataDetail(path: String)
        fun setDataAdapter(listItem: List<ItemsItem>)
    }

    interface ViewModel: BaseContract.BaseViewModel {
        fun getListFollowing(path: String)
        fun getListFollowingLiveData(): LiveData<Resource<List<ItemsItem>?>>
    }

    interface Repository: BaseContract.BaseRepository {
        suspend fun getListFollowing(path: String): List<ItemsItem>
    }
}