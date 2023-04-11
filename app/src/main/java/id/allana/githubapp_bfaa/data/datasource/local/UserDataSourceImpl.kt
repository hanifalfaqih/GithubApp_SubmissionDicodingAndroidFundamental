package id.allana.githubapp_bfaa.data.datasource.local

import id.allana.githubapp_bfaa.data.local.UserDao
import id.allana.githubapp_bfaa.data.model.local.User

class UserDataSourceImpl(private val userDao: UserDao): UserDataSource {

    override suspend fun getAllUser(): List<User> {
        return userDao.getFavoriteUsers()
    }

    override suspend fun getFavoriteUserByUsername(username: String): Boolean {
        return userDao.getFavoriteUserByUsername(username)
    }

    override suspend fun deleteUser(username: String): Int {
        return userDao.deleteFavoriteUser(username)
    }

    override suspend fun insertUser(user: User): Long {
        return userDao.insertFavoriteUser(user)
    }

}