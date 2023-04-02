package id.allana.githubapp_bfaa.ui.detail

import androidx.lifecycle.LiveData
import id.allana.githubapp_bfaa.data.datasource.local.UserDataSource
import id.allana.githubapp_bfaa.data.datasource.network.GithubUserDataSource
import id.allana.githubapp_bfaa.data.model.local.User
import id.allana.githubapp_bfaa.data.model.network.ResponseDetailUser

class DetailUserRepository(
    private val githubUserDataSource: GithubUserDataSource,
    private val userDataSource: UserDataSource
): DetailUserContract.Repository {
    override suspend fun getDetailUser(path: String): ResponseDetailUser {
        return githubUserDataSource.getDetailUser(path)
    }

    override fun getFavoriteUserByUsername(username: String): LiveData<Boolean> {
        return userDataSource.getFavoriteUserByUsername(username)
    }

    override suspend fun deleteUser(username: String): Int {
        return userDataSource.deleteUser(username)
    }

    override suspend fun insertUser(user: User): Long {
        return userDataSource.insertUser(user)
    }
}