package com.example.real_ak_pilsmineder.presentation.viewmodel

import android.app.Application
import android.app.LocaleManager
import android.content.Context
import android.os.Build
import android.os.LocaleList
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.real_ak_pilsmineder.data.preferences.AppPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


class LanguageViewModel(application: Application) : AndroidViewModel(application) {

    private val context: Context = application.applicationContext
    private val appPreferences = AppPreferences(context)

    private val _currentLanguage = MutableStateFlow("en")
    val currentLanguage: StateFlow<String> = _currentLanguage.asStateFlow()

    init {
        appPreferences.languageCodeFlow
            .onEach { savedLang ->
                _currentLanguage.value = savedLang
                applyLocale(savedLang)
            }
            .launchIn(viewModelScope)
    }

    fun changeLanguage(languageCode: String) {
        viewModelScope.launch {
            appPreferences.saveLanguage(languageCode)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                val localeManager = context.getSystemService(Context.LOCALE_SERVICE) as LocaleManager
                localeManager.applicationLocales = LocaleList.forLanguageTags(languageCode)
            }

            _currentLanguage.value = languageCode
        }
    }
    private fun applyLocale(languageCode: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val localeManager = context.getSystemService(Context.LOCALE_SERVICE) as LocaleManager
            if (localeManager.applicationLocales.toLanguageTags() != languageCode) {
                localeManager.applicationLocales = LocaleList.forLanguageTags(languageCode)
            }
        } else {
            val currentAppLocales = AppCompatDelegate.getApplicationLocales()
            if (currentAppLocales.toLanguageTags() != languageCode) {
                val appLocale = LocaleListCompat.forLanguageTags(languageCode)
                AppCompatDelegate.setApplicationLocales(appLocale)
            }
        }
    }
}
