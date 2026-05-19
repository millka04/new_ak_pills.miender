package com.example.real_ak_pilsmineder.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.real_ak_pilsmineder.presentation.screen.AddingScreen
import com.example.real_ak_pilsmineder.presentation.screen.AuthorizationScreen
import com.example.real_ak_pilsmineder.presentation.screen.MainScreen
import com.example.real_ak_pilsmineder.presentation.screen.MedicinesScreen


@Composable
fun PilsNavHost(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Auth.route){
        composable(Screen.Auth.route) {
            AuthorizationScreen(navController)
        }
        composable(Screen.Main.route) {
            MainScreen(modifier = Modifier, navController)
        }
        composable(Screen.Adding.route) {
            AddingScreen(modifier = Modifier, navController)
        }
        composable(Screen.Medicines.route) {
            MedicinesScreen(modifier = Modifier, navController)
        }
    }
}