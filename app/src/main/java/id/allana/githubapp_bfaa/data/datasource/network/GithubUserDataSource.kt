package id.allana.githubapp_bfaa.data.datasource.network

import id.allana.githubapp_bfaa.data.model.network.ItemsItem
import id.allana.githubapp_bfaa.data.model.network.ResponseDetailUser
import id.allana.githubapp_bfaa.data.model.network.ResponseSearchUser

interface GithubUserDataSource {
    suspend fun getSearchUser(query: String): ResponseSearchUser
    suspend fun getDetailUser(path: String): ResponseDetailUser
    suspend fun getFollowingUser(path: String): List<ItemsItem>
    suspend fun getFollowerUser(path: String): List<ItemsItem>
}