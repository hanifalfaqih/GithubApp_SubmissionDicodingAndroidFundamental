package id.allana.githubapp_bfaa.ui.setting

import androidx.lifecycle.LiveData
import id.allana.githubapp_bfaa.data.datasource.preference.PreferenceDataSource

class SettingThemeRepository(private val preferenceDataSource: PreferenceDataSource): SettingThemeContract.Repository {
    override fun getThemeSetting(): LiveData<Boolean> {
        return preferenceDataSource.getThemeSetting()
    }

    override suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        preferenceDataSource.saveThemeSetting(isDarkModeActive)
    }
}