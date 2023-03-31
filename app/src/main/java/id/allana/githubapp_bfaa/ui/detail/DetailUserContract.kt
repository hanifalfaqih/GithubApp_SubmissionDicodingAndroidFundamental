package id.allana.githubapp_bfaa.ui.detail

import androidx.lifecycle.LiveData
import id.allana.githubapp_bfaa.data.base.BaseContract
import id.allana.githubapp_bfaa.data.base.Resource
import id.allana.githubapp_bfaa.data.model.ResponseDetailUser

interface DetailUserContract {

    interface View: BaseContract.BaseView {
        fun getDataDetail(path: String)
        fun setDataDetail(data: ResponseDetailUser)
    }

    interface ViewModel: BaseContract.BaseViewModel {
        fun getDetailUser(path: String)
        fun getDetailUserLiveData(): LiveData<Resource<ResponseDetailUser>>
    }

    interface Repository: BaseContract.BaseRepository {
        suspend fun getDetailUser(path: String): ResponseDetailUser
    }

}