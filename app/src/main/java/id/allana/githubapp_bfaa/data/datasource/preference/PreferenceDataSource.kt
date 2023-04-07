package id.allana.githubapp_bfaa.data.datasource.preference

import androidx.lifecycle.LiveData

interface PreferenceDataSource {
    fun getThemeSetting(): LiveData<Boolean>
    suspend fun saveThemeSetting(isDarkModeActive: Boolean)
}