package id.allana.githubapp_bfaa.ui.setting

import androidx.lifecycle.LiveData
import id.allana.githubapp_bfaa.data.base.BaseContract

interface SettingThemeContract {

    interface View: BaseContract.BaseView {
    }

    interface ViewModel: BaseContract.BaseViewModel {
        fun getThemeSetting(): LiveData<Boolean>
        fun saveThemeSetting(isDarkModeActive: Boolean)
    }

    interface Repository: BaseContract.BaseRepository {
        fun getThemeSetting(): LiveData<Boolean>
        suspend fun saveThemeSetting(isDarkModeActive: Boolean)
    }
}