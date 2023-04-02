package id.allana.githubapp_bfaa.data.network

import id.allana.githubapp_bfaa.data.model.*
import id.allana.githubapp_bfaa.data.model.network.ItemsItem
import id.allana.githubapp_bfaa.data.model.network.ResponseDetailUser
import id.allana.githubapp_bfaa.data.model.network.ResponseSearchUser
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    // Get Search User
    @GET("/search/users?")
    suspend fun getSearchUser(
        @Query("q") q: String
    ): ResponseSearchUser

    // Get Detail User
    @GET("/users/{username}")
    suspend fun getDetailUser(
        @Path("username") username: String
    ): ResponseDetailUser

    // Get Follower from Detail User
    @GET("/users/{username}/followers")
    suspend fun getFollowerUser(
        @Path("username") username: String
    ): List<ItemsItem>

    // Get Following from Detail User
    @GET("/users/{username}/following")
    suspend fun getFollowingUser(
        @Path("username") username: String
    ): List<ItemsItem>
}