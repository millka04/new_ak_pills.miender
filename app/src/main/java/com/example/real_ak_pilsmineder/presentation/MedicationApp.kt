package com.example.real_ak_pilsmineder.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Medication
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.real_ak_pilsmineder.presentation.navigation.AppBottomNavigation
import com.example.real_ak_pilsmineder.presentation.navigation.AppNavigation
import com.example.real_ak_pilsmineder.presentation.screen.CalendarScreen
import com.example.real_ak_pilsmineder.presentation.screen.MedicationsScreen
import com.example.real_ak_pilsmineder.presentation.viewmodel.MedicationViewModel


@Composable
fun MedicationApp() {
    val navController = rememberNavController()
    val viewModel: MedicationViewModel = viewModel()

    Scaffold(
        bottomBar = {
            AppBottomNavigation(navController = navController)
        }
    ) { innerPadding ->
        AppNavigation(
            navController = navController,
            viewModel = viewModel,
            modifier = Modifier.padding(innerPadding)
        )
    }
}