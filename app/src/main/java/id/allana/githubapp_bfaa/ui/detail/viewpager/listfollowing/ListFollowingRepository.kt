package id.allana.githubapp_bfaa.ui.detail.viewpager.listfollowing

import id.allana.githubapp_bfaa.data.datasource.GithubUserDataSource
import id.allana.githubapp_bfaa.data.model.ItemsItem
import id.allana.githubapp_bfaa.data.model.ResponseFollowingUser

class ListFollowingRepository(private val githubUserDataSource: GithubUserDataSource): ListFollowingContract.Repository {
    override suspend fun getListFollowing(path: String): List<ItemsItem> {
        return githubUserDataSource.getFollowingUser(path)
    }
}