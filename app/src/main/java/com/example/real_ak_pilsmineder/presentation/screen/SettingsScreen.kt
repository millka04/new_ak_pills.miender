package com.example.real_ak_pilsmineder.presentation.screen


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.real_ak_pilsmineder.R
import com.example.real_ak_pilsmineder.presentation.viewmodel.LanguageViewModel


@Composable
fun SettingsScreen(languageViewModel: LanguageViewModel = viewModel() ) {

    val currentLang by languageViewModel.currentLanguage.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().background(colorResource(R.color.white)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = stringResource(id = R.string.current_language, currentLang),
            modifier = Modifier.padding(16.dp)
        )
        Button(onClick = {
            val newLang = if (currentLang.startsWith("en")) "ru" else "en"
            languageViewModel.changeLanguage(newLang)
            Log.d("RRR",newLang)
        }) {

            Text(text = stringResource(id = R.string.change_language))
        }
    }
}
