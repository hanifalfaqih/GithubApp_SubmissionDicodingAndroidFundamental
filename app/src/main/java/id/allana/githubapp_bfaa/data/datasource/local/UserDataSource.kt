package id.allana.githubapp_bfaa.data.datasource.local

import androidx.lifecycle.LiveData
import id.allana.githubapp_bfaa.data.model.local.User

interface UserDataSource {

    suspend fun getAllUser(): List<User>
    fun getFavoriteUserByUsername(username: String): LiveData<Boolean>
    suspend fun deleteUser(username: String): Int
    suspend fun insertUser(user: User): Long

}