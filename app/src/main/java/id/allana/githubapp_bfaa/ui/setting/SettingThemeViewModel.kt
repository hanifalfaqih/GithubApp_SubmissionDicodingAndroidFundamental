package id.allana.githubapp_bfaa.ui.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import id.allana.githubapp_bfaa.data.base.BaseViewModelImpl
import kotlinx.coroutines.launch

class SettingThemeViewModel(private val settingThemeRepository: SettingThemeRepository): BaseViewModelImpl(), SettingThemeContract.ViewModel {
    override fun getThemeSetting(): LiveData<Boolean> {
        return settingThemeRepository.getThemeSetting()
    }

    override fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            settingThemeRepository.saveThemeSetting(isDarkModeActive)
        }
    }
}