package id.allana.githubapp_bfaa.data.datasource.local

import id.allana.githubapp_bfaa.data.model.local.User

interface UserDataSource {

    suspend fun getAllUser(): List<User>
    suspend fun getFavoriteUserByUsername(username: String): Boolean
    suspend fun deleteUser(username: String): Int
    suspend fun insertUser(user: User): Long

}