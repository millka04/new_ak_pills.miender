package com.example.real_ak_pilsmineder.presentation.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.magnifier
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.material3.TextField
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.real_ak_pilsmineder.data.HowOften
import com.example.real_ak_pilsmineder.presentation.navigation.Screen

val howOftenList = listOf<HowOften>(
    HowOften("once","Один раз"),
    HowOften("everyday","Каждый день"),
    HowOften("everyweek","Каждую неделю"),
    HowOften("everymounth","Каждый месяц"),
    HowOften("xday","Каждые X дней"),
    HowOften("xweek","Каждые X недель"),
    HowOften("xmounth","Каждые X месяцев"),
    HowOften("every...","Каждые...")
)
val flagCounterList = listOf("xday","xweek", "xmounth")
val flagEvery = listOf("every...")

val weekDaysList = listOf<String>("Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс")




@Composable
fun AddingScreen(modifier: Modifier = Modifier, navController: NavController){
    val selectedItems = remember {
        mutableStateListOf<Int>()
    }
    var howoften by remember { mutableStateOf("")  }
    var med_name by remember { mutableStateOf("") }


    Scaffold() { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues).padding(start = 10.dp, end = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            Row {
                TextField(
                    value = med_name,
                    onValueChange = {
                        med_name = it
                    },
                    modifier = modifier
                        .fillMaxWidth(),
                    label = { Text("Название препарата") }
                )
            }
            Row(
                modifier = modifier.fillMaxWidth().height(60.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = modifier.fillMaxHeight()
                ) {
                    Text(

                        text = "Принимать в течение ",
                        textAlign = TextAlign.Center
                    )
                }
                Column {
                    TextField(
                        value = "",
                        label = { Text("Укажите колчество дней")},
                        onValueChange = {}
                    )
                }

            }

            var expanded by remember { mutableStateOf(false) }
            Row {
                Column {
                    Row(modifier = modifier
                        .clickable { expanded = true }
                    ) {
                        Text(
                            text = "Как часто повторять?",
                            modifier = modifier
                                .padding())
                        Spacer(modifier = Modifier.width(16.dp))
                        Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = "")
                    }
                }
                Column {
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        howOftenList.forEach {
                            DropdownMenuItem(
                                text = { Text(it.text) },
                                onClick = {
                                    howoften = it.key
                                    expanded = false
                                }
                            )
                        }
                    }
                }
                Column {
                    Text(text = howOftenList.find { it.key == howoften }?.text ?: "")
                }
            }
            if(howoften in flagCounterList) {
                Row {
                    TextField(
                        value = "",
                        label = { Text("Количество приемов - X") },
                        onValueChange = {},
                        modifier = modifier
                            .padding()
                    )
                }
            }
            if(howoften in flagEvery) {
                Row {
                    weekDaysList.forEachIndexed { index, item ->
                        val isSelected = index in selectedItems
                        val backgroundColor =
                            if (isSelected)
                                Color.Green
                            else
                                Color.Transparent
                        Box(
                            modifier = modifier
                                .padding(15.dp)
                                .background(backgroundColor)
                                .clickable {
                                    if (isSelected){
                                        selectedItems.remove(index)
                                    }
                                    else{
                                        selectedItems.add(index)
                                    }
                                }
                        ){
                            Text(item)
                        }
                    }
                }
            }
            Button(
                modifier = modifier
                    .align(Alignment.End)
                    .padding(paddingValues).padding(start = 10.dp, end = 10.dp),
                onClick = {navController.navigate(Screen.Main.route)}
            ) {
                Text("Сохранить")
            }
        }
    }
}