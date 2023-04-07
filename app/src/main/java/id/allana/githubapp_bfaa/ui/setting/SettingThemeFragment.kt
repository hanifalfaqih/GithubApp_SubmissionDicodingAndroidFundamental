package id.allana.githubapp_bfaa.ui.setting

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.preferences.preferencesDataStore
import id.allana.githubapp_bfaa.data.base.BaseFragment
import id.allana.githubapp_bfaa.data.base.GenericViewModelFactory
import id.allana.githubapp_bfaa.data.datasource.preference.PreferenceDataSourceImpl
import id.allana.githubapp_bfaa.data.datasource.preference.SettingThemePreference
import id.allana.githubapp_bfaa.databinding.FragmentSettingThemeBinding

class SettingThemeFragment : BaseFragment<FragmentSettingThemeBinding, SettingThemeViewModel>(
    FragmentSettingThemeBinding::inflate
), SettingThemeContract.View {

    private val Context.dataStore by preferencesDataStore("setting")

    override fun initView() {
        switchTheme()
    }

    private fun switchTheme() {
        getViewBinding().switchTheme.setOnCheckedChangeListener { _, isChecked ->
            getViewModel().saveThemeSetting(isChecked)
        }
    }

    override fun initViewModel(): SettingThemeViewModel {
        val settingThemePreference = SettingThemePreference.getInstance(requireContext().dataStore)
        val dataSource = PreferenceDataSourceImpl(settingThemePreference)
        val repository = SettingThemeRepository(dataSource)
        return GenericViewModelFactory(SettingThemeViewModel(repository)).create(SettingThemeViewModel::class.java)
    }

    override fun observeData() {
        super.observeData()
        getViewModel().getThemeSetting().observe(viewLifecycleOwner) { isDarkModeActive ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                getViewBinding().switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                getViewBinding().switchTheme.isChecked = false
            }
        }
    }
}