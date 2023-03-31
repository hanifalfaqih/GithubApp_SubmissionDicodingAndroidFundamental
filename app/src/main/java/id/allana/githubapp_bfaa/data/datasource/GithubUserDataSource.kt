package id.allana.githubapp_bfaa.data.datasource

import id.allana.githubapp_bfaa.data.model.*

interface GithubUserDataSource {
    suspend fun getSearchUser(query: String): ResponseSearchUser
    suspend fun getDetailUser(path: String): ResponseDetailUser
    suspend fun getFollowingUser(path: String): List<ItemsItem>
    suspend fun getFollowerUser(path: String): List<ItemsItem>
}