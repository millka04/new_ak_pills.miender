package com.example.real_ak_pilsmineder.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.CalendarViewMonth
import androidx.compose.material.icons.filled.Medication
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.real_ak_pilsmineder.R
import com.example.real_ak_pilsmineder.presentation.theme.PilulaColor
import com.example.real_ak_pilsmineder.presentation.theme.StatusBarColor

@Composable
fun AppBottomNavigation(navController: NavHostController) {
    NavigationBar(
        containerColor = StatusBarColor
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        NavigationBarItem(
            selected = currentRoute == Screen.Calendar.route,
            onClick = {
                navController.navigate(Screen.Calendar.route) {
                    popUpTo(Screen.Calendar.route) { inclusive = true }
                }
            },
            icon = { Icon(Icons.Default.CalendarViewMonth, contentDescription = null) },
            label = { Text(stringResource(R.string.screen_calendar)) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = colorResource(R.color.black),
                selectedTextColor = colorResource(R.color.black),
                indicatorColor = PilulaColor,
                unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )

        NavigationBarItem(
            selected = currentRoute == Screen.Medications.route,
            onClick = {
                navController.navigate(Screen.Medications.route) {
                    popUpTo(Screen.Medications.route) { inclusive = true }
                }
            },
            icon = { Icon(Icons.Default.Medication, contentDescription = null) },
            label = { Text(stringResource(R.string.screen_medications)) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = colorResource(R.color.black),
                selectedTextColor = colorResource(R.color.black),
                indicatorColor = PilulaColor,
                unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
            )

        )

        NavigationBarItem(
            selected = currentRoute == Screen.Settings.route,
            onClick = { navController.navigate(Screen.Settings.route) { popUpTo(Screen.Settings.route) { inclusive = true } } },
            icon = { Icon(Icons.Default.Settings, contentDescription = null) },
            label = { Text(stringResource(R.string.screen_settings)) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = colorResource(R.color.black),
                selectedTextColor = colorResource(R.color.black),
                indicatorColor = PilulaColor,
                unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )
    }
}