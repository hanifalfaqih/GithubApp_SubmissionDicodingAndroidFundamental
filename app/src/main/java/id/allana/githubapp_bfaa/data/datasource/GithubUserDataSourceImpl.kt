package id.allana.githubapp_bfaa.data.datasource

import id.allana.githubapp_bfaa.data.model.*
import id.allana.githubapp_bfaa.data.network.ApiConfig

class GithubUserDataSourceImpl: GithubUserDataSource {

    override suspend fun getSearchUser(query: String): ResponseSearchUser {
        return ApiConfig.getApiService().getSearchUser(query)
    }

    override suspend fun getDetailUser(path: String): ResponseDetailUser {
        return ApiConfig.getApiService().getDetailUser(path)
    }

    override suspend fun getFollowingUser(path: String): List<ItemsItem> {
        return ApiConfig.getApiService().getFollowingUser(path)
    }

    override suspend fun getFollowerUser(path: String): List<ItemsItem> {
        return ApiConfig.getApiService().getFollowerUser(path)
    }

}