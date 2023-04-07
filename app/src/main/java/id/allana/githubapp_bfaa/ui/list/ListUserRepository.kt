package id.allana.githubapp_bfaa.ui.list

import androidx.lifecycle.LiveData
import id.allana.githubapp_bfaa.data.datasource.network.GithubUserDataSource
import id.allana.githubapp_bfaa.data.datasource.preference.PreferenceDataSource
import id.allana.githubapp_bfaa.data.model.network.ResponseSearchUser

class ListUserRepository(
    private val githubUserDataSource: GithubUserDataSource,
    private val preferenceDataSource: PreferenceDataSource
): ListUserContract.Repository {
    override fun getThemeSetting(): LiveData<Boolean> {
        return preferenceDataSource.getThemeSetting()
    }

    override suspend fun getSearchUser(query: String): ResponseSearchUser {
        return githubUserDataSource.getSearchUser(query)
    }

}