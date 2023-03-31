package id.allana.githubapp_bfaa.ui.detail

import id.allana.githubapp_bfaa.data.datasource.GithubUserDataSource
import id.allana.githubapp_bfaa.data.model.ResponseDetailUser

class DetailUserRepository(private val githubUserDataSource: GithubUserDataSource): DetailUserContract.Repository {
    override suspend fun getDetailUser(path: String): ResponseDetailUser {
        return githubUserDataSource.getDetailUser(path)
    }
}