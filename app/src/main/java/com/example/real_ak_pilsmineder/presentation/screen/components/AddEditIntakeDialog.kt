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
import androidx.compose.material3.*
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditIntakeDialog(
    intakeWithMed: IntakeWithMed?,
    medications: List<Medication>,
    onDismiss: () -> Unit,
    onSave: (Intake) -> Unit
) {
    val context = LocalContext.current

    var selectedMedId by remember {
        mutableStateOf(intakeWithMed?.medication?.id ?: medications.firstOrNull()?.id ?: 0L)
    }
    var duringDay by remember {
        mutableStateOf(intakeWithMed?.intake?.duringDay ?: (8 * 60))
    }
    var often by remember {
        mutableStateOf(intakeWithMed?.intake?.often ?: "everyday")
    }
    var weekday by remember {
        mutableStateOf(intakeWithMed?.intake?.weekday ?: "1111100")
    }

    var showTimePicker by remember { mutableStateOf(false) }
    var expandedMed by remember { mutableStateOf(false) }
    var expandedOften by remember { mutableStateOf(false) }

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

                if (showTimePicker) {
                    TimePickerDialog(
                        context,
                        { _, hour, minute ->
                            duringDay = hour * 60 + minute
                            showTimePicker = false
                        },
                        duringDay / 60,
                        duringDay % 60,
                        true
                    ).show()
                    showTimePicker = false
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

                Spacer(modifier = Modifier.height(12.dp))

                Text("Дни недели:", style = MaterialTheme.typography.labelMedium)
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    listOf("Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс").forEachIndexed { index, label ->
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            // убираем 48dp для размера по умолчанию
                            CompositionLocalProvider(LocalMinimumInteractiveComponentSize provides 10.dp) {
                                Checkbox(
                                    checked = weekday.getOrNull(index) == '1',
                                    onCheckedChange = { isChecked ->
                                        val chars = weekday.toCharArray()
                                        chars[index] = if (isChecked) '1' else '0'
                                        weekday = String(chars)
                                    }
                                )
                            }
                            Text(label, fontSize = 8.sp)
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (weekday.all { it == '0' }) {
                        return@TextButton
                    }
                    val newIntake = Intake(
                        id = intakeWithMed?.intake?.id ?: 0,
                        preparatId = selectedMedId,
                        duringDay = duringDay,
                        often = often,
                        weekday = weekday
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
}