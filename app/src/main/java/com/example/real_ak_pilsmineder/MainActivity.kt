package com.example.real_ak_pilsmineder

import android.app.AlarmManager
import android.app.LocaleManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.LocaleList
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.core.os.LocaleListCompat
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.real_ak_pilsmineder.data.preferences.AppPreferences
import com.example.real_ak_pilsmineder.presentation.MedicationApp
import com.example.real_ak_pilsmineder.presentation.theme.Real_Ak_pilsminederTheme
import com.example.real_ak_pilsmineder.presentation.theme.StatusBarColor
import com.example.real_ak_pilsmineder.presentation.viewmodel.LanguageViewModel
import com.example.real_ak_pilsmineder.utils.NotificationUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor = StatusBarColor.toArgb()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility =
                window.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        NotificationUtils.createNotificationChannel(this)

        setContent {
            val languageViewModel: LanguageViewModel = viewModel()
            val currentLang by languageViewModel.currentLanguage.collectAsState()

            Log.d("RRR", "CurrentLang=$currentLang")
            val configuration = LocalConfiguration.current
            val context = LocalContext.current

            val locale = Locale(currentLang)
            Locale.setDefault(locale)

            configuration.setLocale(locale)

            val updatedContext = context.createConfigurationContext(configuration)

            CompositionLocalProvider(
                LocalConfiguration provides configuration,
                LocalContext provides updatedContext
            ) {
                Real_Ak_pilsminederTheme {
                    MedicationApp()
                }
            }
        }
    }
}