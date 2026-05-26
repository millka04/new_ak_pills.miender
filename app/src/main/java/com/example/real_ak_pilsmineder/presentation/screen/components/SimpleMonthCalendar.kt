package com.example.real_ak_pilsmineder.presentation.screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.real_ak_pilsmineder.domain.model.IntakeWithMed
import com.example.real_ak_pilsmineder.presentation.theme.PilulaColor
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.*

@Composable
fun SimpleMonthCalendar(
    currentMonth: YearMonth,
    selectedDate: LocalDate,
    intakesByDate: Map<LocalDate, List<IntakeWithMed>> = emptyMap(),
    onDateSelected: (LocalDate) -> Unit,
    onMonthChange: (YearMonth) -> Unit
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = { onMonthChange(currentMonth.minusMonths(1)) }) {
                Icon(Icons.Default.ChevronLeft, contentDescription = null)
            }

            Text(
                text = currentMonth.format(DateTimeFormatter.ofPattern("MMMM yyyy", Locale("ru"))),
                style = MaterialTheme.typography.titleMedium,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f)
            )

            IconButton(onClick = { onMonthChange(currentMonth.plusMonths(1)) }) {
                Icon(Icons.Default.ChevronRight, contentDescription = null)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            listOf("Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс").forEach { day ->
                Text(day, modifier = Modifier.weight(1f), textAlign = TextAlign.Center, style = MaterialTheme.typography.labelSmall, color = Color.Gray)
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        val firstDayOffset = currentMonth.atDay(1).dayOfWeek.value - 1
        val daysInMonth = currentMonth.lengthOfMonth()
        val colors = listOf(Color(0xFFE53935), Color(0xFF1E88E5), Color(0xFF43A047), Color(0xFF8E24AA), Color(0xFFFB8C00))

        LazyVerticalGrid(columns = GridCells.Fixed(7), modifier = Modifier.height(280.dp)) {
            items(firstDayOffset) { Box(Modifier.size(40.dp)) }

            items(daysInMonth) { index ->
                val day = index + 1
                val date = currentMonth.atDay(day)
                val isSelected = date == selectedDate
                val dayIntakes = intakesByDate[date] ?: emptyList()

                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clickable { onDateSelected(date) }
                        .background(
                            if (isSelected) PilulaColor else Color.Transparent,
                            CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = day.toString(),
                        color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Black,
                        style = MaterialTheme.typography.bodyMedium
                    )

                    if (dayIntakes.isNotEmpty()) {
                        Row(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .offset(y = (-3).dp),
                            horizontalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            dayIntakes.take(3).forEachIndexed { i, _ ->
                                Box(
                                    modifier = Modifier
                                        .size(5.dp)
                                        .background(colors[i % colors.size], CircleShape)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}