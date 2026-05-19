package com.example.real_ak_pilsmineder.presentation.navigation

sealed class Screen(val route: String) {
    object Auth: Screen("auth")
    object Main: Screen("main")
    object Reg: Screen("reg")
    object Adding: Screen("adding")
    object Medicines: Screen("medic")
}