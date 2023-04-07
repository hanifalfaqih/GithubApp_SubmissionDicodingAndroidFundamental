package id.allana.githubapp_bfaa.data.datasource.preference

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData

class PreferenceDataSourceImpl(private val settingThemePreference: SettingThemePreference): PreferenceDataSource {
    override fun getThemeSetting(): LiveData<Boolean> {
        return settingThemePreference.getThemeSetting().asLiveData()
    }

    override suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        settingThemePreference.saveThemeSetting(isDarkModeActive)
    }
}