package com.example.real_ak_pilsmineder.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.real_ak_pilsmineder.data.preferences.AppPreferences
import com.example.real_ak_pilsmineder.presentation.screen.CalendarScreen
import com.example.real_ak_pilsmineder.presentation.screen.MedicationsScreen
import com.example.real_ak_pilsmineder.presentation.screen.SettingsScreen
import com.example.real_ak_pilsmineder.presentation.viewmodel.MedicationViewModel


@Composable
fun AppNavigation(
    navController: NavHostController,
    viewModel: MedicationViewModel,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Calendar.route,
        modifier = modifier
    ) {
        composable(Screen.Calendar.route) {
            CalendarScreen(viewModel = viewModel)
        }
        composable(Screen.Medications.route) {
            MedicationsScreen(viewModel = viewModel)
        }
        composable(Screen.Settings.route) {
            SettingsScreen()
        }
    }
}