package id.allana.githubapp_bfaa.ui.favorite

import androidx.lifecycle.LiveData
import id.allana.githubapp_bfaa.data.datasource.local.UserDataSource
import id.allana.githubapp_bfaa.data.model.local.User

class ListUserFavoriteRepository(private val userDataSource: UserDataSource): ListUserFavoriteContract.Repository {
    override fun getAllUser(): LiveData<List<User>> {
        return userDataSource.getAllUser()
    }
}