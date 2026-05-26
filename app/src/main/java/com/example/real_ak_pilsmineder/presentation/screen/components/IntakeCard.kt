package com.example.real_ak_pilsmineder.presentation.screen.components

import com.example.real_ak_pilsmineder.domain.model.Intake
import com.example.real_ak_pilsmineder.domain.model.IntakeWithMed
import com.example.real_ak_pilsmineder.domain.model.Medication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.real_ak_pilsmineder.presentation.theme.ItemColor

import java.time.LocalDate
import java.time.YearMonth


@Composable
fun IntakeCard(
    item: IntakeWithMed,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = ItemColor
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(item.medication.name, style = MaterialTheme.typography.titleMedium)
                Text(
                    text = "${item.intake.duringDay.toTimeString()} • ${getFrequencyLabel(item.intake.often)}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            IconButton(onClick = onEdit) { Icon(Icons.Default.Edit, null) }
            IconButton(onClick = onDelete) { Icon(Icons.Default.Delete, null) }
        }
    }
}

fun Int.toTimeString(): String = "%02d:%02d".format(this / 60, this % 60)

fun getFrequencyLabel(often: String): String = when (often.lowercase()) {
    "everyday", "every" -> "Каждый день"
    "everyweek" -> "Каждую неделю"
    "everymonth" -> "Каждый месяц"
    else -> often
}
