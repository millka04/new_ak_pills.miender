package com.example.real_ak_pilsmineder.presentation.screen



import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.real_ak_pilsmineder.presentation.navigation.Screen

import dev.alejo.compose_calendar.CalendarEvent
import dev.alejo.compose_calendar.ComposeCalendar
import dev.alejo.compose_calendar.SimpleComposeCalendar
import dev.alejo.compose_calendar.util.CalendarDefaults
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale


@Composable
fun MainScreen(modifier: Modifier = Modifier, navController: NavController) {

    val events = getListOfEvents().map{ it.date }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SimpleComposeCalendar(
            modifier = Modifier.padding(16.dp),
            events = events,
            onDayClick = { date, event ->
                println(date.toString())
                println(event.toString())
            },
            eventIndicator = { _, _, _ ->
                IndicatorContent(Modifier.fillMaxWidth().height(8.dp))
            }
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            ) {
            Button(
                modifier = modifier.weight(0.5f),
                onClick = { navController.navigate(Screen.Medicines.route) }
            ) {
                Text("Добавить новое лекарство")
            }
            Button(
                modifier = modifier.weight(0.5f),
                onClick = { navController.navigate(Screen.Adding.route) }
            ) {
                Text("Добавить прием")
            }
        }
    }
}


private fun getListOfEvents() = listOf(
    CalendarEvent(
        data = MyData("Event 1"),
        date = LocalDate.now()
    ),
    CalendarEvent(
        data = MyData("Event 1"),
        date = LocalDate.now().minusDays(20)
    ),
    CalendarEvent(
        data = MyData("Event 2"),
        date = LocalDate.now().minusDays(20)
    ),
    CalendarEvent(
        data = MyData("Event 1"),
        date = LocalDate.now().plusDays(1)
    ),
    CalendarEvent(
        data = MyData("Event 2"),
        date = LocalDate.now().plusDays(1)
    ),
    CalendarEvent(
        data = MyData("Event 3"),
        date = LocalDate.now().plusDays(1)
    ),
    CalendarEvent(
        data = MyData("Event 4"),
        date = LocalDate.now().plusDays(1)
    ),
    CalendarEvent(
        data = MyData("Event 3"),
        date = LocalDate.now().plusDays(4)
    ),
    CalendarEvent(
        data = MyData("Event 4"),
        date = LocalDate.now().plusDays(4)
    ),
    CalendarEvent(
        data = MyData("Event 4"),
        date = LocalDate.now().plusDays(4)
    ),
)

data class MyData(val name: String)

@Composable
fun IndicatorContent(modifier: Modifier = Modifier, color: Color = Color.Black) {
    Box(modifier.clip(CircleShape).background(color))
}



/*@Composable
fun DeyCall(
    day: Int,
    isToday: Boolean,
//    isCurMonth: Boolean,
    modifier: Modifier = Modifier
){
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(
                when{
                    isToday -> MaterialTheme.colorScheme.primaryContainer
                    else -> Color.Transparent
                }
            )
    ){
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = day.toString()
            //не получается добавить color
        )
    }
}


@Composable
fun MainScreen2(){
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val curMonth = YearMonth.now()
            val MonthName = curMonth.month.getDisplayName(
                TextStyle.FULL,
                Locale("ru")
            )
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                Text(
                    text = "$MonthName ${curMonth.year}"
                )
            }

            Row(modifier = Modifier.fillMaxWidth()) {
                listOf<String>("Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс").forEach { day ->
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(day)
                    }
                }
            }

            val today = LocalDate.now()
            val yearMonth = YearMonth.now()
            val firstDayOfMonth = yearMonth.atDay(1)
            var curDate = firstDayOfMonth
            while (curDate.dayOfWeek.value != 1) {
                curDate = curDate.minusDays(1)
            }
            Column(modifier = Modifier.fillMaxWidth()) {
                for (week in 0..5) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        for (dayInd in 1..7) {
                            DeyCall(
                                day = curDate.dayOfMonth,
                                isToday = curDate == today,
                                modifier = Modifier.weight(1f)
                            )
                            curDate = curDate.plusDays(1)
                        }
                    }
                }
            }

            Button(
                modifier = Modifier.align(Alignment.End),
                onClick = {}
            ) {
                Text("Добавить новое лекарство")
            }
        }
    }
}*/