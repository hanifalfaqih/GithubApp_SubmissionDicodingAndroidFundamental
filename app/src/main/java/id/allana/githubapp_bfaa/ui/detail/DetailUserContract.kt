package id.allana.githubapp_bfaa.ui.detail

import androidx.lifecycle.LiveData
import id.allana.githubapp_bfaa.data.base.BaseContract
import id.allana.githubapp_bfaa.data.base.Resource
import id.allana.githubapp_bfaa.data.model.local.User
import id.allana.githubapp_bfaa.data.model.network.ResponseDetailUser

interface DetailUserContract {

    interface View: BaseContract.BaseView {
        fun getDataDetail(path: String)
        fun getStatusFavorite(path: String)
        fun setDataDetail(data: ResponseDetailUser)
    }

    interface ViewModel: BaseContract.BaseViewModel {
        fun getDetailUser(path: String)
        fun getDetailUserLiveData(): LiveData<Resource<ResponseDetailUser>>
        fun getFavoriteUserByUsername(username: String)
        fun getFavoriteUserByUsernameLiveData(): LiveData<Boolean>
        fun deleteUser(username: String)
        fun deleteUserLiveData(): LiveData<Resource<Int>>
        fun insertUser(user: User)
        fun insertUserLiveData(): LiveData<Resource<Long>>
    }

    interface Repository: BaseContract.BaseRepository {
        suspend fun getDetailUser(path: String): ResponseDetailUser
        suspend fun getFavoriteUserByUsername(username: String): Boolean
        suspend fun deleteUser(username: String): Int
        suspend fun insertUser(user: User): Long
    }

}