package id.allana.githubapp_bfaa.ui.splash

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import id.allana.githubapp_bfaa.R
import id.allana.githubapp_bfaa.data.base.GenericViewModelFactory
import id.allana.githubapp_bfaa.data.datasource.preference.PreferenceDataSourceImpl
import id.allana.githubapp_bfaa.data.datasource.preference.SettingThemePreference
import id.allana.githubapp_bfaa.ui.home.HomeActivity
import id.allana.githubapp_bfaa.ui.setting.SettingThemeRepository
import id.allana.githubapp_bfaa.ui.setting.SettingThemeViewModel
import kotlinx.coroutines.*

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private val Context.dataStore by preferencesDataStore("setting")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        supportActionBar?.hide()

        lifecycleScope.launch {
            val settingThemePreference =
                SettingThemePreference.getInstance(this@SplashActivity.dataStore)
            val dataSource = PreferenceDataSourceImpl(settingThemePreference)
            val repository = SettingThemeRepository(dataSource)
            val viewModel = GenericViewModelFactory(SettingThemeViewModel(repository)).create(
                SettingThemeViewModel::class.java
            )
            withContext(Dispatchers.Main) {
                delay(3000).apply {
                    val intentToHome = Intent(this@SplashActivity, HomeActivity::class.java)
                    intentToHome.flags =
                        Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intentToHome)
                    finish()
                }
                viewModel.getThemeSetting().observe(this@SplashActivity) { isDarkModeActive ->
                    if (isDarkModeActive) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    } else {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    }
                }
            }
        }
    }
}