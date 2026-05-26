package com.example.real_ak_pilsmineder.presentation.screen

import androidx.compose.foundation.background
import com.example.real_ak_pilsmineder.domain.model.IntakeWithMed
import com.example.real_ak_pilsmineder.presentation.screen.components.AddEditIntakeDialog
import com.example.real_ak_pilsmineder.presentation.screen.components.IntakeCard
import com.example.real_ak_pilsmineder.presentation.screen.components.SimpleMonthCalendar
import com.example.real_ak_pilsmineder.presentation.viewmodel.MedicationViewModel

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.real_ak_pilsmineder.R
import com.example.real_ak_pilsmineder.domain.model.Intake
import com.example.real_ak_pilsmineder.presentation.theme.PilulaColor
import com.example.real_ak_pilsmineder.presentation.theme.StatusBarColor
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(viewModel: MedicationViewModel) {
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var currentMonth by remember { mutableStateOf(YearMonth.now()) }
    var showAddDialog by remember { mutableStateOf(false) }
    var editingIntake by remember { mutableStateOf<IntakeWithMed?>(null) }

    val scope = rememberCoroutineScope()

    val allIntakes by viewModel.allIntakes.collectAsState()
    val medications by viewModel.medications.collectAsState()

    var intakesForSelectedDate by remember { mutableStateOf(emptyList<IntakeWithMed>()) }

    val intakesByDate by remember(currentMonth, allIntakes, medications) {
        derivedStateOf {
            val medMap = medications.associateBy { it.id }
            val result = mutableMapOf<LocalDate, MutableList<IntakeWithMed>>()
            val today = LocalDate.now()

            val start = currentMonth.atDay(1)
            val end = currentMonth.atEndOfMonth()
            var current = start

            while (!current.isAfter(end)) {
                if (current.isBefore(today)) {
                    current = current.plusDays(1)
                    continue
                }

                val dayIntakes = allIntakes
                    .filter { intake ->
                        intake.weekday.length == 7 &&
                                intake.weekday[when (current.dayOfWeek) {
                                    java.time.DayOfWeek.MONDAY -> 0
                                    java.time.DayOfWeek.TUESDAY -> 1
                                    java.time.DayOfWeek.WEDNESDAY -> 2
                                    java.time.DayOfWeek.THURSDAY -> 3
                                    java.time.DayOfWeek.FRIDAY -> 4
                                    java.time.DayOfWeek.SATURDAY -> 5
                                    java.time.DayOfWeek.SUNDAY -> 6
                                }] == '1'
                    }
                    .mapNotNull { intake ->
                        medMap[intake.preparatId]?.let { med ->
                            IntakeWithMed(intake, med)
                        }
                    }

                if (dayIntakes.isNotEmpty()) {
                    result[current] = dayIntakes.toMutableList()
                }
                current = current.plusDays(1)
            }
            result
        }
    }

    LaunchedEffect(selectedDate) {
        intakesForSelectedDate = viewModel.getIntakesForDate(selectedDate)
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.screen_calendar)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = StatusBarColor,
                    titleContentColor = colorResource(R.color.black)
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier.fillMaxSize().background(colorResource(R.color.white))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                    SimpleMonthCalendar(
                        currentMonth = currentMonth,
                        selectedDate = selectedDate,
                        intakesByDate = intakesByDate,
                        onDateSelected = { selectedDate = it },
                        onMonthChange = { currentMonth = it }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Приёмы на ${selectedDate.format(DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale("ru")))}",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    if (intakesForSelectedDate.isEmpty()) {
                        Text("На этот день нет приёмов", modifier = Modifier.padding(16.dp), color = Color.Gray)
                    } else {
                        LazyColumn {
                            items(intakesForSelectedDate) { item ->
                                IntakeCard(
                                    item = item,
                                    onEdit = {
                                        editingIntake = item
                                        showAddDialog = true
                                    },
                                    onDelete = {
                                        viewModel.deleteIntake(item.intake)

                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.weight(1f))


                if (showAddDialog) {
                    AddEditIntakeDialog(
                        intakeWithMed = editingIntake,
                        medications = medications,
                        onDismiss = { showAddDialog = false },
                        onSave = { newIntake ->
                            val medName = medications.find { it.id == newIntake.preparatId }?.name ?: ""

                            viewModel.addOrUpdateIntake(newIntake, medName)

                            scope.launch {
                                intakesForSelectedDate = viewModel.getIntakesForDate(selectedDate)
                            }

                            showAddDialog = false
                        }
                    )
                }
            }
            FloatingActionButton(
                onClick = {
                    editingIntake = null
                    showAddDialog = true
                },
                modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp),
                containerColor = PilulaColor
            ) {
                Icon(Icons.Default.Add, contentDescription = "Добавить приём")
            }

        }
}


}