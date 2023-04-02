package id.allana.githubapp_bfaa.data.datasource.network

import id.allana.githubapp_bfaa.data.model.network.ItemsItem
import id.allana.githubapp_bfaa.data.model.network.ResponseDetailUser
import id.allana.githubapp_bfaa.data.model.network.ResponseSearchUser
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