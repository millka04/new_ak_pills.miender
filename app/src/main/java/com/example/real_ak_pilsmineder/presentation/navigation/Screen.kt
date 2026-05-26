package com.example.real_ak_pilsmineder.presentation.navigation

sealed class Screen(val route: String, val label: String) {
    object Calendar : Screen("calendar", "Приём")
    object Medications : Screen("meds", "Препараты")
    object Settings : Screen("settings", "Настройки")
}