package id.allana.githubapp_bfaa.ui.list

import id.allana.githubapp_bfaa.data.datasource.GithubUserDataSource
import id.allana.githubapp_bfaa.data.model.ItemsItem
import id.allana.githubapp_bfaa.data.model.ResponseSearchUser

class ListUserRepository(private val githubUserDataSource: GithubUserDataSource): ListUserContract.Repository {

    override suspend fun getSearchUser(query: String): ResponseSearchUser {
        return githubUserDataSource.getSearchUser(query)
    }

}