package com.example.real_ak_pilsmineder.presentation.screen.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import com.example.real_ak_pilsmineder.domain.model.Intake
import com.example.real_ak_pilsmineder.domain.model.IntakeWithMed
import com.example.real_ak_pilsmineder.domain.model.Medication
import android.app.TimePickerDialog
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditIntakeDialog(
    intakeWithMed: IntakeWithMed?,
    medications: List<Medication>,
    onDismiss: () -> Unit,
    onSave: (Intake) -> Unit
) {
    val context = LocalContext.current
    val today = LocalDate.now()

    var selectedMedId by remember {
        mutableStateOf(intakeWithMed?.medication?.id ?: medications.firstOrNull()?.id ?: 0L)
    }
    var duringDay by remember {
        mutableStateOf(intakeWithMed?.intake?.duringDay ?: (9 * 60))
    }
    var often by remember {
        mutableStateOf(intakeWithMed?.intake?.often ?: "everyday")
    }
    var weekday by remember {
        mutableStateOf(intakeWithMed?.intake?.weekday ?: "0000000")
    }
    var selectedDate by remember {
        mutableStateOf(intakeWithMed?.intake?.date ?: today)
    }

    var showTimePicker by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }
    var expandedMed by remember { mutableStateOf(false) }
    var expandedOften by remember { mutableStateOf(false) }

    var length by remember { mutableStateOf("")}

    val isOneTime = often == "once"
    val isEveryweekTime = often == "everyweek"
    val isEverymonthTime = often == "everymonth"

    val timePickerState = rememberTimePickerState(
        initialHour = duringDay / 60,
        initialMinute = duringDay % 60,
        is24Hour = true
    )

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = selectedDate.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()
    )

    val frequencyOptions = listOf(
        "everyday" to "Каждый день",
        "everyweek" to "Каждую неделю",
        "everymonth" to "Каждый месяц",
        "once" to "Одноразово"
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(if (intakeWithMed == null) "Добавить приём" else "Редактировать приём")
        },
        text = {
            Column {
                ExposedDropdownMenuBox(
                    expanded = expandedMed,
                    onExpandedChange = { expandedMed = it }
                ) {
                    OutlinedTextField(
                        value = medications.find { it.id == selectedMedId }?.name ?: "Выберите препарат",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Препарат") },
                        modifier = Modifier.menuAnchor().fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = expandedMed,
                        onDismissRequest = { expandedMed = false }
                    ) {
                        medications.forEach { med ->
                            DropdownMenuItem(
                                text = { Text(med.name) },
                                onClick = {
                                    selectedMedId = med.id
                                    expandedMed = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedButton(
                    onClick = { showTimePicker = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Время: ${duringDay.toTimeString()}")
                }

                Spacer(modifier = Modifier.height(12.dp))

                ExposedDropdownMenuBox(
                    expanded = expandedOften,
                    onExpandedChange = { expandedOften = it }
                ) {
                    OutlinedTextField(
                        value = frequencyOptions.find { it.first == often }?.second ?: often,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Частота") },
                        modifier = Modifier.menuAnchor().fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = expandedOften,
                        onDismissRequest = { expandedOften = false }
                    ) {
                        frequencyOptions.forEach { (key, label) ->
                            DropdownMenuItem(
                                text = { Text(label) },
                                onClick = {
                                    often = key
                                    expandedOften = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (isOneTime) {
                    Text("Дата приёма:", style = MaterialTheme.typography.labelMedium)
                    OutlinedButton(
                        onClick = { showDatePicker = true },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            selectedDate.format(DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale("ru")))
                        )
                    }
                }
                else if (isEveryweekTime) {
                    Text("Дни недели:", style = MaterialTheme.typography.labelMedium)
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        listOf("Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс").forEachIndexed { index, label ->
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                CompositionLocalProvider(LocalMinimumInteractiveComponentSize provides 10.dp) {
                                    Checkbox(
                                        checked = weekday.getOrNull(index) == '1',
                                        onCheckedChange = { checked ->
                                            val chars = weekday.toCharArray()
                                            chars[index] = if (checked) '1' else '0'
                                            weekday = String(chars)
                                        }
                                    )
                                }
                                Text(label, fontSize = 8.sp)
                            }
                        }
                    }
                }
                else  {
                    Text("Принимать в течении:", style = MaterialTheme.typography.labelMedium)
                    OutlinedTextField(
                        value = length,
                        onValueChange = { length = it.filter { it.isDigit() } },
                        modifier = Modifier,
                        label = { Text("Количество дней приема") },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        singleLine = true
                    )
                    Text("Дата приёма:", style = MaterialTheme.typography.labelMedium)
                    OutlinedButton(
                        onClick = { showDatePicker = true },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            selectedDate.format(DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale("ru")))
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (isEveryweekTime && weekday.all { it == '0' }) {
                        return@TextButton
                    }
                    if (isEverymonthTime && length.toInt() > 28) {
                        return@TextButton
                    }
                    val newIntake = Intake(
                        id = intakeWithMed?.intake?.id ?: 0L,
                        preparatId = selectedMedId,
                        duringDay = duringDay,
                        often = often,
                        weekday = if (isOneTime) "0000000" else if (isEveryweekTime) weekday else "0000000",
                        date = if (isEveryweekTime) null else selectedDate,
                        length = if (isOneTime) 1 else if (isEveryweekTime) 0 else length.toInt()
                    )
                    onSave(newIntake)
                }
            ) {
                Text("Сохранить")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Отмена")
            }
        }
    )
    if (showTimePicker) {
        AlertDialog(
            onDismissRequest = { showTimePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    duringDay = timePickerState.hour * 60 + timePickerState.minute
                    showTimePicker = false
                }) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { showTimePicker = false }) { Text("Отмена") }
            },
            text = {
                TimePicker(state = timePickerState)
            }
        )
    }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        selectedDate = Instant.ofEpochMilli(millis)
                            .atZone(ZoneOffset.UTC)
                            .toLocalDate()
                    }
                    showDatePicker = false
                }) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) { Text("Отмена") }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}