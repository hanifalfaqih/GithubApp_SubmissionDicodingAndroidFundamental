package id.allana.githubapp_bfaa.ui.detail.viewpager.listfollower

import id.allana.githubapp_bfaa.data.datasource.network.GithubUserDataSource
import id.allana.githubapp_bfaa.data.model.network.ItemsItem

class ListFollowerRepository(private val githubUserDataSource: GithubUserDataSource): ListFollowerContract.Repository {
    override suspend fun getListFollower(path: String): List<ItemsItem> {
        return githubUserDataSource.getFollowerUser(path)
    }
}