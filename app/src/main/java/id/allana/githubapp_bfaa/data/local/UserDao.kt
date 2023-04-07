package id.allana.githubapp_bfaa.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import id.allana.githubapp_bfaa.data.model.local.User

@Dao
interface UserDao {

    @Query("SELECT * FROM FavoriteUsers")
    fun getFavoriteUsers(): LiveData<List<User>>

    @Query("SELECT EXISTS(SELECT * FROM FavoriteUsers WHERE username = :username)")
    fun getFavoriteUserByUsername(username: String): LiveData<Boolean>

    @Query("DELETE FROM FavoriteUsers WHERE username = :username")
    suspend fun deleteFavoriteUser(username: String): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteUser(user: User): Long

}